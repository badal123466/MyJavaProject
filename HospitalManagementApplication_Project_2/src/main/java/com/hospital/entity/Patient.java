package com.hospital.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patient_seq"
    )
    @SequenceGenerator(
            name = "patient_seq",
            sequenceName = "PATIENT_SEQ",
            allocationSize = 1
    )
    private Long id;

    // Patient unique hospital code
    @Column(unique = true, nullable = false)
    private String patientCode;

    private String name;

    private int age;

    private String gender;

    private String phone;

    private String email;

    private String address;

    private String bloodGroup;


    // Default constructor
    public Patient() {
    }


    // Generate patient code automatically before INSERT
    @PrePersist
    public void generatePatientCode() {

        if (patientCode == null) {
            patientCode = "PAT-" + System.currentTimeMillis();
        }
    }


    // Get ID
    public Long getId() {
        return id;
    }


    // Get Patient Code
    public String getPatientCode() {
        return patientCode;
    }

    // Set Patient Code
    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }


    // Get Name
    public String getName() {
        return name;
    }

    // Set Name
    public void setName(String name) {
        this.name = name;
    }


    // Get Age
    public int getAge() {
        return age;
    }

    // Set Age
    public void setAge(int age) {
        this.age = age;
    }


    // Get Gender
    public String getGender() {
        return gender;
    }

    // Set Gender
    public void setGender(String gender) {
        this.gender = gender;
    }


    // Get Phone
    public String getPhone() {
        return phone;
    }

    // Set Phone
    public void setPhone(String phone) {
        this.phone = phone;
    }


    // Get Email
    public String getEmail() {
        return email;
    }

    // Set Email
    public void setEmail(String email) {
        this.email = email;
    }


    // Get Address
    public String getAddress() {
        return address;
    }

    // Set Address
    public void setAddress(String address) {
        this.address = address;
    }


    // Get Blood Group
    public String getBloodGroup() {
        return bloodGroup;
    }

    // Set Blood Group
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}