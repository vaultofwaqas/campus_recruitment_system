package com.waqkz.campusrecruitmentsystem;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFormFragment extends Fragment {

    private ImageView companyImage;
    private EditText companyAddress;
    private EditText companyPhoneNumber;
    private EditText companyWebPage;
    private EditText companyVacancies;
    private Button companySaveButton;

    private String mCompanyAddress;
    private String mCompanyPhoneNumber;
    private String mCompanyWebPage;
    private String mCompanyVacancies;

    private Uri mImageUri = null;

    private static int Gallery_Request = 1;
    private Intent galleyIntent;

    public HashMap<String, String> hashMap = new HashMap<>();
    private String url;

    private StorageReference mStorage;

    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgressDialog;

    public CompanyFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_company_form, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mStorage = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog(getActivity());

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        attachingWidgets(rootView);
        initializingComponents();

        return rootView;
    }

    public void attachingWidgets(View view){

        companyAddress = (EditText) view.findViewById(R.id.company_adress);
        companyPhoneNumber = (EditText) view.findViewById(R.id.company_phone_number);
        companyWebPage = (EditText) view.findViewById(R.id.company_web_page);
        companyVacancies = (EditText) view.findViewById(R.id.company_vacancies);
        companySaveButton = (Button) view.findViewById(R.id.company_save);
    }

    public void initializingComponents(){

        companySaveButton.setOnClickListener(v -> addingCompanyForm());
    }

    public void addingCompanyForm(){

        mProgressDialog.setMessage("Adding Form ...");
        mProgressDialog.show();

        mCompanyAddress = companyAddress.getText().toString();
        mCompanyPhoneNumber = companyPhoneNumber.getText().toString();
        mCompanyWebPage = companyWebPage.getText().toString();
        mCompanyVacancies = companyVacancies.getText().toString();

        if (TextUtils.isEmpty(mCompanyAddress)
                && TextUtils.isEmpty(mCompanyPhoneNumber)
                && TextUtils.isEmpty(mCompanyWebPage)
                && TextUtils.isEmpty(mCompanyVacancies)) {

            mProgressDialog.dismiss();

            companyAddress.setError(getString(R.string.company_address));
            companyPhoneNumber.setError(getString(R.string.phone_number));
            companyWebPage.setError(getString(R.string.company_web_page));
            companyVacancies.setError(getString(R.string.vacancies_available));

            return;
        }

        CompanyForm companyForm = new CompanyForm(companyAddress.getText().toString(),
                companyPhoneNumber.getText().toString(),
                companyWebPage.getText().toString(),
                companyVacancies.getText().toString());

        mDatabase.child("Campus").child("Company-Form").child(currentUser.getUid()).setValue(companyForm);

        mProgressDialog.dismiss();

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_two, new StudentListFragment());
    }
}
