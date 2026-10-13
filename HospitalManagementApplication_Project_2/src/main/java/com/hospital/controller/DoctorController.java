package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.dto.DoctorRequest;
import com.hospital.entity.Doctor;
import com.hospital.repository.DoctorRepository;
import com.hospital.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {

    private final DoctorRepository doctorRepository;

    private final DoctorService doctorService;


    public DoctorController(
            DoctorRepository doctorRepository,
            DoctorService doctorService) {

        this.doctorRepository =
                doctorRepository;

        this.doctorService =
                doctorService;
    }


    // GET ALL DOCTORS

    @GetMapping
    public ResponseEntity<List<Doctor>>
            getAllDoctors() {

        return ResponseEntity.ok(
            doctorRepository.findAll()
        );
    }


    // ADD DOCTOR

    @PostMapping
    public ResponseEntity<Doctor>
            addDoctor(
                @RequestBody DoctorRequest request) {

        Doctor doctor =
                doctorService.addDoctor(
                    request
                );

        return ResponseEntity.ok(
            doctor
        );
    }


    // DELETE DOCTOR

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
            deleteDoctor(
                @PathVariable Long id) {

        if (!doctorRepository
                .existsById(id)) {

            return ResponseEntity
                    .notFound()
                    .build();
        }


        doctorRepository.deleteById(id);


        return ResponseEntity.ok(
            "Doctor deleted successfully"
        );
    }
}