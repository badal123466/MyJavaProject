
package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.entity.Patient;
import com.hospital.service.PatientService;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    // =========================
    // SEE ALL PATIENTS
    // =========================
    @GetMapping
    public ResponseEntity<List<Patient>> getAll() {

        List<Patient> patients =
                service.getAllPatients();

        return ResponseEntity.ok(patients);
    }

    // =========================
    // SEE ONE PATIENT
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getPatient(id)
        );
    }

    // =========================
    // SAVE PATIENT
    // =========================
    @PostMapping
    public ResponseEntity<Patient> save(
            @RequestBody Patient patient) {

        return ResponseEntity.ok(
                service.savePatient(patient)
        );
    }

    // =========================
    // DELETE PATIENT
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        service.deletePatient(id);

        return ResponseEntity.ok(
                "Patient deleted successfully"
        );
    }
}
