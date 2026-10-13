package com.hospital.service;

import com.hospital.entity.Department;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DepartmentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(
            DepartmentRepository repository) {

        this.repository = repository;
    }

    public List<Department> getAll() {
        return repository.findAll();
    }

    public Department getById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found"
                        ));
    }

    public Department save(Department department) {

        return repository.save(department);
    }

    public Department update(
            Long id,
            Department department) {

        Department existing = getById(id);

        existing.setName(department.getName());
        existing.setDescription(
                department.getDescription()
        );
        existing.setLocation(
                department.getLocation()
        );

        return repository.save(existing);
    }

    public void delete(Long id) {

        Department department = getById(id);

        repository.delete(department);
    }
}