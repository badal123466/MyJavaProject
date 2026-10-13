package com.hospital.dto;

public class DoctorRequest {

    private String name;

    private String specialization;

    private String phone;

    private String email;

    private String qualification;

    private double consultationFee;

    private Long departmentId;


    public DoctorRequest() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(
            String specialization) {

        this.specialization =
            specialization;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getQualification() {
        return qualification;
    }

    public void setQualification(
            String qualification) {

        this.qualification =
            qualification;
    }


    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(
            double consultationFee) {

        this.consultationFee =
            consultationFee;
    }


    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
            Long departmentId) {

        this.departmentId =
            departmentId;
    }
}