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

    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrollment>>> getAllEnrollments(
            @RequestParam(required = false) String searchStudentName
    ) {
        List<Enrollment> enrollments =
                enrollmentService.getAllEnrollments(searchStudentName);

        return ResponseEntity.ok(
                ApiResponse.success("Thành công", enrollments)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> getEnrollmentById(@PathVariable long id) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentById(id);
            return ResponseEntity.ok(
                    ApiResponse.success("Thành công", enrollment)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Enrollment>> createEnrollment(
            @RequestBody Enrollment newEnrollment
    ) {
        try {
            Enrollment created = enrollmentService.createEnrollment(newEnrollment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Thành công", created));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> updateEnrollment(
            @PathVariable long id,
            @RequestBody Enrollment newEnrollment
    ) {
        try {
            Enrollment updated =
                    enrollmentService.updateEnrollment(id, newEnrollment);

            return ResponseEntity.ok(
                    ApiResponse.success("Thành công", updated)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEnrollment(@PathVariable long id) {
        try {
            enrollmentService.deleteEnrollmentById(id);
            return ResponseEntity.ok(
                    ApiResponse.success("Thành công", null)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
