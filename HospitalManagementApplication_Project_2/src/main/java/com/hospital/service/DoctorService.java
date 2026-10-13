package com.hospital.service;

import org.springframework.stereotype.Service;

import com.hospital.dto.DoctorRequest;
import com.hospital.entity.Department;
import com.hospital.entity.Doctor;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final DepartmentRepository departmentRepository;


    public DoctorService(
            DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository) {

        this.doctorRepository =
                doctorRepository;

        this.departmentRepository =
                departmentRepository;
    }


    public Doctor addDoctor(
            DoctorRequest request) {


        Department department =
                departmentRepository
                    .findById(
                        request.getDepartmentId()
                    )
                    .orElseThrow(
                        () -> new RuntimeException(
                            "Department not found"
                        )
                    );


        Doctor doctor =
                new Doctor();


        doctor.setName(
            request.getName()
        );

        doctor.setSpecialization(
            request.getSpecialization()
        );

        doctor.setPhone(
            request.getPhone()
        );

        doctor.setEmail(
            request.getEmail()
        );

        doctor.setQualification(
            request.getQualification()
        );

        doctor.setConsultationFee(
            request.getConsultationFee()
        );


        // IMPORTANT
        doctor.setDepartment(
            department
        );


        return doctorRepository.save(
            doctor
        );
    }
}