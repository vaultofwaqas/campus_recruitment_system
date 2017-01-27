package com.waqkz.campusrecruitmentsystem;

/**
 * Created by waqkz on 1/26/17.
 */

public class StudentForm {

    private String studentLastSemester;
    private String studentCGPA;
    private String studentPhoneNumber;
    private String studentAcademicHonors;
    private String studentCommunityService;
    private String studentWorkExperiance;
    private String studentHobbies;

    public StudentForm() {
    }

    public StudentForm(String studentLastSemester,
                       String studentCGPA,
                       String studentPhoneNumber,
                       String studentAcademicHonors,
                       String studentCommunityService,
                       String studentWorkExperiance,
                       String studentHobbies) {

        this.studentLastSemester = studentLastSemester;
        this.studentCGPA = studentCGPA;
        this.studentPhoneNumber = studentPhoneNumber;
        this.studentAcademicHonors = studentAcademicHonors;
        this.studentCommunityService = studentCommunityService;
        this.studentWorkExperiance = studentWorkExperiance;
        this.studentHobbies = studentHobbies;
    }

    public String getStudentLastSemester() {
        return studentLastSemester;
    }

    public void setStudentLastSemester(String studentLastSemester) {
        this.studentLastSemester = studentLastSemester;
    }

    public String getStudentCGPA() {
        return studentCGPA;
    }

    public void setStudentCGPA(String studentCGPA) {
        this.studentCGPA = studentCGPA;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public void setStudentPhoneNumber(String studentPhoneNumber) {
        this.studentPhoneNumber = studentPhoneNumber;
    }

    public String getStudentAcademicHonors() {
        return studentAcademicHonors;
    }

    public void setStudentAcademicHonors(String studentAcademicHonors) {
        this.studentAcademicHonors = studentAcademicHonors;
    }

    public String getStudentCommunityService() {
        return studentCommunityService;
    }

    public void setStudentCommunityService(String studentCommunityService) {
        this.studentCommunityService = studentCommunityService;
    }

    public String getStudentWorkExperiance() {
        return studentWorkExperiance;
    }

    public void setStudentWorkExperiance(String studentWorkExperiance) {
        this.studentWorkExperiance = studentWorkExperiance;
    }

    public String getStudentHobbies() {
        return studentHobbies;
    }

    public void setStudentHobbies(String studentHobbies) {
        this.studentHobbies = studentHobbies;
    }
}
