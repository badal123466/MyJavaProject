package com.hospital.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.entity.Appointment;
import com.hospital.entity.AppointmentStatus;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.exception.BusinessException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    // =========================================================
    // CONSTRUCTOR INJECTION
    // =========================================================

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    // =========================================================
    // GET ALL APPOINTMENTS
    // =========================================================

    public List<Appointment> getAll() {

        return appointmentRepository.findAll();
    }

    // =========================================================
    // GET APPOINTMENT BY ID
    // =========================================================

    public Appointment getById(Long id) {

        if (id == null) {
            throw new BusinessException(
                    "Appointment ID is required"
            );
        }

        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: " + id
                        )
                );
    }

    // =========================================================
    // CREATE APPOINTMENT
    // =========================================================

    public Appointment create(
            Appointment appointment,
            Long patientId,
            Long doctorId) {

        // -----------------------------------------------------
        // CHECK APPOINTMENT OBJECT
        // -----------------------------------------------------

        if (appointment == null) {
            throw new BusinessException(
                    "Appointment data is required"
            );
        }

        // -----------------------------------------------------
        // CHECK PATIENT ID
        // -----------------------------------------------------

        if (patientId == null) {
            throw new BusinessException(
                    "Patient ID is required"
            );
        }

        // -----------------------------------------------------
        // CHECK DOCTOR ID
        // -----------------------------------------------------

        if (doctorId == null) {
            throw new BusinessException(
                    "Doctor ID is required"
            );
        }

        // -----------------------------------------------------
        // FIND PATIENT
        // -----------------------------------------------------

        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: "
                                        + patientId
                        )
                );

        // -----------------------------------------------------
        // FIND DOCTOR
        // -----------------------------------------------------

        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "
                                        + doctorId
                        )
                );

        // -----------------------------------------------------
        // CHECK APPOINTMENT DATE
        // -----------------------------------------------------

        if (appointment.getAppointmentDate() == null) {

            throw new BusinessException(
                    "Appointment date is required"
            );
        }

        // -----------------------------------------------------
        // CHECK APPOINTMENT TIME
        // -----------------------------------------------------

        if (appointment.getAppointmentTime() == null) {

            throw new BusinessException(
                    "Appointment time is required"
            );
        }

        // -----------------------------------------------------
        // CHECK PAST DATE
        // -----------------------------------------------------

        if (appointment.getAppointmentDate()
                .isBefore(LocalDate.now())) {

            throw new BusinessException(
                    "Appointment date cannot be in the past"
            );
        }

        // -----------------------------------------------------
        // FIND DOCTOR'S APPOINTMENTS
        // -----------------------------------------------------

        List<Appointment> existingAppointments =
                appointmentRepository
                        .findByDoctorId(doctorId);

        // -----------------------------------------------------
        // CHECK DOCTOR DOUBLE BOOKING
        // -----------------------------------------------------

        boolean alreadyBooked =
                existingAppointments.stream()
                        .anyMatch(existing -> {

                            if (existing.getAppointmentDate()
                                    == null) {

                                return false;
                            }

                            if (existing.getAppointmentTime()
                                    == null) {

                                return false;
                            }

                            if (existing.getStatus() == null) {

                                return false;
                            }

                            boolean sameDate =
                                    existing
                                            .getAppointmentDate()
                                            .equals(
                                                    appointment
                                                            .getAppointmentDate()
                                            );

                            boolean sameTime =
                                    existing
                                            .getAppointmentTime()
                                            .equals(
                                                    appointment
                                                            .getAppointmentTime()
                                            );

                            boolean activeAppointment =
                                    existing.getStatus()
                                            == AppointmentStatus.SCHEDULED
                                    ||
                                    existing.getStatus()
                                            == AppointmentStatus.CONFIRMED;

                            return sameDate
                                    && sameTime
                                    && activeAppointment;
                        });

        // -----------------------------------------------------
        // DOCTOR ALREADY BOOKED
        // -----------------------------------------------------

        if (alreadyBooked) {

            throw new BusinessException(
                    "Doctor is already booked at this date and time"
            );
        }

        // -----------------------------------------------------
        // SET PATIENT
        // -----------------------------------------------------

        appointment.setPatient(patient);

        // -----------------------------------------------------
        // SET DOCTOR
        // -----------------------------------------------------

        appointment.setDoctor(doctor);

        // -----------------------------------------------------
        // DEFAULT STATUS
        // -----------------------------------------------------

        if (appointment.getStatus() == null) {

            appointment.setStatus(
                    AppointmentStatus.SCHEDULED
            );
        }

        // -----------------------------------------------------
        // SAVE APPOINTMENT
        // -----------------------------------------------------

        return appointmentRepository.save(
                appointment
        );
    }

    // =========================================================
    // TODAY'S APPOINTMENTS
    // =========================================================

    public List<Appointment> today() {

        return appointmentRepository
                .findByAppointmentDate(
                        LocalDate.now()
                );
    }

    // =========================================================
    // GET APPOINTMENTS BY DOCTOR
    // =========================================================

    public List<Appointment> getByDoctor(
            Long doctorId) {

        if (doctorId == null) {

            throw new BusinessException(
                    "Doctor ID is required"
            );
        }

        // Check doctor exists
        doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "
                                        + doctorId
                        )
                );

        return appointmentRepository
                .findByDoctorId(doctorId);
    }

    // =========================================================
    // GET APPOINTMENTS BY PATIENT
    // =========================================================

    public List<Appointment> getByPatient(
            Long patientId) {

        if (patientId == null) {

            throw new BusinessException(
                    "Patient ID is required"
            );
        }

        // Check patient exists
        patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: "
                                        + patientId
                        )
                );

        return appointmentRepository
                .findByPatientId(patientId);
    }

    // =========================================================
    // CANCEL APPOINTMENT
    // =========================================================

    public Appointment cancel(Long id) {

        Appointment appointment =
                getById(id);

        if (appointment.getStatus()
                == AppointmentStatus.COMPLETED) {

            throw new BusinessException(
                    "Completed appointment cannot be cancelled"
            );
        }

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new BusinessException(
                    "Appointment is already cancelled"
            );
        }

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );

        return appointmentRepository.save(
                appointment
        );
    }

    // =========================================================
    // CONFIRM APPOINTMENT
    // =========================================================

    public Appointment confirm(Long id) {

        Appointment appointment =
                getById(id);

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new BusinessException(
                    "Cancelled appointment cannot be confirmed"
            );
        }

        if (appointment.getStatus()
                == AppointmentStatus.COMPLETED) {

            throw new BusinessException(
                    "Completed appointment cannot be confirmed"
            );
        }

        appointment.setStatus(
                AppointmentStatus.CONFIRMED
        );

        return appointmentRepository.save(
                appointment
        );
    }

    // =========================================================
    // COMPLETE APPOINTMENT
    // =========================================================

    public Appointment complete(Long id) {

        Appointment appointment =
                getById(id);

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new BusinessException(
                    "Cancelled appointment cannot be completed"
            );
        }

        appointment.setStatus(
                AppointmentStatus.COMPLETED
        );

        return appointmentRepository.save(
                appointment
        );
    }
}