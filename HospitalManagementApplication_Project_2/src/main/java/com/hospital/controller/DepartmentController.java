package com.hospital.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hospital.entity.Department;
import com.hospital.repository.DepartmentRepository;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(
            DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    // GET ALL DEPARTMENTS
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentRepository.findAll()
        );
    }

    // ADD DEPARTMENT
    @PostMapping
    public ResponseEntity<Department> addDepartment(
            @RequestBody Department department) {

        Department saved =
                departmentRepository.save(department);

        return ResponseEntity.ok(saved);
    }

    // DELETE DEPARTMENT
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(
            @PathVariable Long id) {

        if (!departmentRepository.existsById(id)) {

            return ResponseEntity.notFound().build();
        }

        departmentRepository.deleteById(id);

        return ResponseEntity.ok(
                "Department deleted successfully"
        );
    }
}