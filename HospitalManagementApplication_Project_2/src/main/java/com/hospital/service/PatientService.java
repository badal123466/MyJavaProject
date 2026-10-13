
package com.hospital.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // SEE ALL PATIENTS
    public List<Patient> getAllPatients() {

        return patientRepository.findAll();
    }

    // SEE ONE PATIENT
    public Patient getPatient(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Patient not found with ID: " + id
                    )
                );
    }

    // SAVE PATIENT
    public Patient savePatient(Patient patient) {

        return patientRepository.save(patient);
    }

    // DELETE PATIENT
    public void deletePatient(Long id) {

        Patient patient = getPatient(id);

        patientRepository.delete(patient);
    }
}

