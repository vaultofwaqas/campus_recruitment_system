package com.waqkz.campusrecruitmentsystem.AccountInfoFlow;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;
import com.waqkz.campusrecruitmentsystem.AccountCreationFlow.AccountCreationActivity;
import com.waqkz.campusrecruitmentsystem.AccountListFlow.AccountListActivity;
import com.waqkz.campusrecruitmentsystem.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AccountInfoActivity extends AppCompatActivity {

    private String membershipType;

    private LinearLayout linearStudentLayout;
    private LinearLayout linearCompanyLayout;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ProgressDialog mProgressDialog;

    private Intent galleryIntent;
    private static int Gallery_Request = 9162;

    private ImageView userImageView;

    private EditText studentName;
    private EditText studentID;
    private EditText studentPhoneNumber;
    private TextView studentDateOfBirth;
    private EditText studentMarks;
    private RadioGroup studentRadioGroup;
    private RadioButton studentMaleRadioButton;
    private RadioButton studentFemaleRadioButton;
    private Button studentSaveAndContinue;

    private String studentNameString;
    private String studentIDString;
    private String studentPhoneNumberString;
    private String studentDateOfBirthString;
    private String studentMarksString;
    private String studentGenderString;

    private EditText companyName;
    private EditText companyAddress;
    private EditText companyPhoneNumber;
    private EditText companyWebPage;
    private CheckBox companyVacancyAvailableCheck;
    private Button companySaveAndContinue;

    private String companyNameString;
    private String companyAddressString;
    private String companyPhoneNumberString;
    private String companyWebPageString;
    private Boolean companyVacancyCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= 21)
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

        Intent intent = getIntent();
        membershipType = intent.getExtras().getString("memberType");

        attachingWidgets();

        if (membershipType.equals(getString(R.string.student_type))){

            linearStudentLayout.setVisibility(View.VISIBLE);
            linearCompanyLayout.setVisibility(View.GONE);

            userImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_student));

        } else if (membershipType.equals(getString(R.string.company_type))){

            linearStudentLayout.setVisibility(View.GONE);
            linearCompanyLayout.setVisibility(View.VISIBLE);

            userImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_company_image));
        }

        mProgressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(FirebaseStorage.getInstance().getReference()));

        initializingWidgets();
    }

    public void attachingWidgets(){

        linearStudentLayout = (LinearLayout) findViewById(R.id.students_info);
        linearCompanyLayout = (LinearLayout) findViewById(R.id.company_info);

        userImageView = (ImageView) findViewById(R.id.user_default_image);

        studentName = (EditText) findViewById(R.id.student_name);
        studentID = (EditText) findViewById(R.id.student_id);
        studentPhoneNumber = (EditText) findViewById(R.id.student_phone_number);
        studentDateOfBirth = (TextView) findViewById(R.id.student_date_of_birth);
        studentMarks = (EditText) findViewById(R.id.student_marks);
        studentRadioGroup = (RadioGroup) findViewById(R.id.student_radio_group);
        studentMaleRadioButton = (RadioButton) findViewById(R.id.student_male_radio_button);
        studentFemaleRadioButton = (RadioButton) findViewById(R.id.student_female_radio_button);
        studentSaveAndContinue = (Button) findViewById(R.id.student_save_continue);

        companyName = (EditText) findViewById(R.id.company_name);
        companyAddress = (EditText) findViewById(R.id.company_address);
        companyPhoneNumber = (EditText) findViewById(R.id.company_phone_number);
        companyWebPage = (EditText) findViewById(R.id.company_web_page);
        companyVacancyAvailableCheck = (CheckBox) findViewById(R.id.vacancy_check);
        companySaveAndContinue = (Button) findViewById(R.id.company_save_continue);
    }

    public void initializingWidgets(){

        if (membershipType.equals(getString(R.string.student_type))){

            userImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("*/*");
                    startActivityForResult(galleryIntent, Gallery_Request);
                }
            });

            studentDateOfBirth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            String dateOfBirth = dayOfMonth + "/" + (month + 1) + "/" + year;
                            studentDateOfBirth.setText(dateOfBirth);
                        }
                    });

                    datePickerFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });

            studentSaveAndContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    studentNameString = studentName.getText().toString().trim();
                    studentIDString = studentID.getText().toString().trim();
                    studentPhoneNumberString = studentPhoneNumber.getText().toString().trim();
                    studentDateOfBirthString = studentDateOfBirth.getText().toString().trim();
                    studentMarksString = studentMarks.getText().toString().trim();

                    if (TextUtils.isEmpty(studentNameString)
                            && TextUtils.isEmpty(studentIDString)
                            && studentPhoneNumberString.equals("")
                            && TextUtils.isEmpty(studentPhoneNumberString)
                            && TextUtils.isEmpty(studentMarksString)) {

                        studentName.setError(getString(R.string.enter_student_name));
                        studentID.setError(getString(R.string.enter_student_id));
                        studentDateOfBirth.setError(getString(R.string.enter_date_of_birth));
                        studentPhoneNumber.setError(getString(R.string.enter_student_phone_number));
                        studentMarks.setError(getString(R.string.enter_marks));

                        return;
                    }

                    if (TextUtils.isEmpty(studentNameString)){

                        studentName.setError(getString(R.string.enter_student_name));
                        return;

                    } else if (TextUtils.isEmpty(studentIDString)){

                        studentID.setError(getString(R.string.enter_student_id));
                        return;

                    } else if(studentDateOfBirthString.equals("")){

                        studentDateOfBirth.setError(getString(R.string.enter_date_of_birth));
                        return;

                    } else if (TextUtils.isEmpty(studentPhoneNumberString)){

                        studentPhoneNumber.setError(getString(R.string.enter_student_phone_number));
                        return;

                    } else if (TextUtils.isEmpty(studentMarksString)){

                        studentMarks.setError(getString(R.string.enter_marks));
                        return;

                    }

                    if(userImageView.getTag().equals("0")){

                        Toast.makeText(AccountInfoActivity.this, getString(R.string.student_profile_image_not_set),
                                Toast.LENGTH_SHORT).show();

                        return;
                    }

                    if (studentRadioGroup.getCheckedRadioButtonId() == -1){

                        Toast.makeText(AccountInfoActivity.this, getString(R.string.select_students_gender),
                                Toast.LENGTH_SHORT).show();

                        return;
                    }

                    if (studentMaleRadioButton.isChecked()){

                        studentGenderString = getString(R.string.student_male);

                    } else if (studentFemaleRadioButton.isChecked()){

                        studentGenderString = getString(R.string.student_female);
                    }

                    mProgressDialog.setMessage("Updating Student Account ...");
                    mProgressDialog.show();

                    uploadFileToFileStorage(v, userImageView, mStorage.child("img/profile"),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(), new ServiceListener<String>() {
                        @Override
                        public void success(String url) {

                            StudentInfo studentInfo = new StudentInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                    studentNameString,
                                    studentIDString,
                                    studentPhoneNumberString,
                                    studentDateOfBirthString,
                                    studentMarksString,
                                    studentGenderString,
                                    url);

                            mDatabase.child(getString(R.string.campus))
                                    .child(getString(R.string.student_type))
                                    .child(getString(R.string.student_info))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(studentInfo);

                            mProgressDialog.dismiss();
                            openAccountListDetailActivity();
                            Snackbar.make(v, "Student Account Update Successful",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            finish();

                        }

                        @Override
                        public void error(ServiceError serviceError) {

                            mProgressDialog.dismiss();
                            Snackbar.make(v, "Student Account Update Was Not Successful",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    });

                }
            });
        }

        if (membershipType.equals(getString(R.string.company_type))){

            userImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("*/*");
                    startActivityForResult(galleryIntent, Gallery_Request);
                }
            });

            companyVacancyAvailableCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    companyVacancyCheck = isChecked;
                }
            });

            companySaveAndContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    companyNameString = companyName.getText().toString().trim();
                    companyAddressString = companyAddress.getText().toString().trim();
                    companyPhoneNumberString = companyPhoneNumber.getText().toString().trim();
                    companyWebPageString = companyWebPage.getText().toString().trim();

                    if (TextUtils.isEmpty(companyNameString)
                            && TextUtils.isEmpty(companyAddressString)
                            && TextUtils.isEmpty(companyPhoneNumberString)
                            && TextUtils.isEmpty(companyWebPageString)) {

                        companyName.setError(getString(R.string.enter_student_name));
                        companyAddress.setError(getString(R.string.enter_student_id));
                        companyPhoneNumber.setError(getString(R.string.enter_student_phone_number));
                        companyWebPage.setError(getString(R.string.enter_date_of_birth));

                        return;
                    }

                    if (TextUtils.isEmpty(companyNameString)){

                        companyName.setError(getString(R.string.enter_student_name));
                        return;

                    } else if (TextUtils.isEmpty(companyAddressString)){

                        companyAddress.setError(getString(R.string.enter_student_id));
                        return;

                    } else if (TextUtils.isEmpty(companyPhoneNumberString)){

                        companyPhoneNumber.setError(getString(R.string.enter_student_phone_number));
                        return;

                    } else if (TextUtils.isEmpty(companyWebPageString)){

                        companyWebPage.setError(getString(R.string.enter_date_of_birth));
                        return;
                    }

                    if(userImageView.getTag().equals("0")){

                        Toast.makeText(AccountInfoActivity.this, getString(R.string.company_profile_image_not_set),
                                Toast.LENGTH_SHORT).show();

                        return;
                    }

                    mProgressDialog.setMessage("Updating Company Account ...");
                    mProgressDialog.show();

                    uploadFileToFileStorage(v, userImageView, mStorage.child("img/profile"),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(), new ServiceListener<String>() {
                        @Override
                        public void success(String url) {

                            CompanyInfo companyInfo = new CompanyInfo(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                    companyNameString,
                                    companyAddressString,
                                    companyPhoneNumberString,
                                    companyWebPageString,
                                    companyVacancyCheck,
                                    url);

                            mDatabase.child(getString(R.string.campus))
                                    .child(getString(R.string.company_type))
                                    .child(getString(R.string.company_info))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(companyInfo);

                            mProgressDialog.dismiss();
                            openAccountListDetailActivity();
                            Snackbar.make(v, "Company Account Update Successful",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            finish();
                        }

                        @Override
                        public void error(ServiceError serviceError) {

                            mProgressDialog.dismiss();
                            Snackbar.make(v, "Company Account Update Was Not Successful",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Crop.REQUEST_PICK && resultCode == this.RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(this.getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == this.RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Crop.getOutput(result));
                userImageView.setImageBitmap(bitmap);
                userImageView.setTag(1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void uploadFileToFileStorage(final View view, ImageView uploadedImage,
                                               StorageReference storageReference, String id, final ServiceListener listener) {

        if (AccountCreationActivity.checkConnectivity(AccountCreationActivity.getContext())) {

            uploadedImage.setDrawingCacheEnabled(true);
            uploadedImage.buildDrawingCache();

            Bitmap bitmap = uploadedImage.getDrawingCache();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            UploadTask uploadTask = storageReference.child(id + ".png").putBytes(bytes);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Snackbar.make(view, R.string.error_upload_image + e.getMessage(),
                            Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    listener.success(taskSnapshot.getDownloadUrl().toString());
                }
            });
        } else {
            Snackbar.make(view, "No Internet Connection Available",
                    Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    public void openAccountListDetailActivity(){

        Intent intent = new Intent(this, AccountListActivity.class);
        intent.putExtra("memberType", membershipType);
        startActivity(intent);
    }
}
