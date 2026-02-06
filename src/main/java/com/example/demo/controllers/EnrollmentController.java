package com.example.demo.controllers;

import com.example.demo.models.Enrollment;
import com.example.demo.services.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // GET /api/enrollments?searchStudentName=abc
    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments(
            @RequestParam(required = false) String searchStudentName
    ) {
        List<Enrollment> enrollments =
                enrollmentService.getAllEnrollments(searchStudentName);

        return ResponseEntity.ok(enrollments);
    }

    // GET /api/enrollments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if (enrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(enrollment);
    }

    // POST /api/enrollments
    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(
            @RequestBody Enrollment newEnrollment
    ) {
        Enrollment created = enrollmentService.createEnrollment(newEnrollment);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/enrollments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(
            @PathVariable long id,
            @RequestBody Enrollment newEnrollment
    ) {
        Enrollment updated =
                enrollmentService.updateEnrollment(id, newEnrollment);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/enrollments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable long id) {
        boolean deleted = enrollmentService.deleteEnrollmentById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
