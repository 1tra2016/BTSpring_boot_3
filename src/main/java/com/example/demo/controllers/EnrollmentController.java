package com.example.demo.controllers;

import com.example.demo.models.Enrollment;
import com.example.demo.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments(
            @RequestParam(required = false) String searchStudentName
    ) {
        List<Enrollment>  enrollments = enrollmentService.getAllEnrollments();
        if(enrollments.isEmpty()) return  ResponseEntity.ok().build();
        if(searchStudentName!=null ) {
            enrollments = enrollments
                        .stream()
                        .filter(e -> e.getStudentName().toLowerCase().contains(searchStudentName.toLowerCase()))
                        .toList();
        }
        return ResponseEntity.ok().body(enrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable long id){
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        if(enrollment == null) return ResponseEntity.notFound().build();
        else return  ResponseEntity.ok().body(enrollment);
    }

    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment newEnrollment) {
        Enrollment enrollment = enrollmentService.createEnrollment(newEnrollment);
        if(enrollment == null) return ResponseEntity.badRequest().body(enrollment);
        return ResponseEntity.ok().body(enrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(
            @PathVariable long id,
            @RequestBody Enrollment newEnrollment
    ){
        Enrollment enrollment = enrollmentService.updateEnrollment(id, newEnrollment);
        if(enrollment == null) return ResponseEntity.badRequest().body(enrollment);
        return ResponseEntity.ok().body(enrollment);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Enrollment> deleteEnrollment(
            @PathVariable long id
    ){
        if(enrollmentService.deleteEnrollmentById(id)==false) return ResponseEntity.badRequest().build();
        else  return ResponseEntity.ok().build();
    }

}
