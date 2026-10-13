package com.hospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String doctorCode;

    private String name;

    private String specialization;

    private String phone;

    private String email;

    private String qualification;

    private double consultationFee;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;


    @PrePersist
    public void generateDoctorCode() {

        if (doctorCode == null ||
            doctorCode.isEmpty()) {

            doctorCode =
                "DOC-" +
                System.currentTimeMillis();
        }
    }


    public Doctor() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
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


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(
            Department department) {

        this.department =
            department;
    }
}