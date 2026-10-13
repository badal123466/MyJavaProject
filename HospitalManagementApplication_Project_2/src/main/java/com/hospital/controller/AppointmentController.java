package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.service.AppointmentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(
            AppointmentService service) {

        this.service = service;
    }

    @GetMapping
    public List<Appointment> getAll() {

        return service.getAll();
    }

    @GetMapping("/today")
    public List<Appointment> today() {

        return service.today();
    }

    @PostMapping
    public Appointment create(
            @RequestBody Appointment appointment,
            @RequestParam Long patientId,
            @RequestParam Long doctorId) {

        return service.create(
                appointment,
                patientId,
                doctorId
        );
    }
}