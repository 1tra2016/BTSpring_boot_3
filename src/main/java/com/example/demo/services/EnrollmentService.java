package com.example.demo.services;

import com.example.demo.models.Enrollment;
import com.example.demo.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Enrollment> getAllEnrollments(String searchStudentName){
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        if(searchStudentName!=null){
            enrollments = enrollments
                    .stream()
                    .filter(e -> e.getStudentName().toLowerCase().contains(searchStudentName.toLowerCase()))
                    .toList();
        }

        return enrollments;
    }

//    public Enrollment findById(Long id){
//        return enrollmentRepository.findById(id).orElse(null);
//    }

    public Enrollment  getEnrollmentById(Long id){
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found id: " + id));
    }

    public Enrollment createEnrollment(Enrollment enrollment){
        return enrollmentRepository.save(enrollment);
    }
    public Enrollment updateEnrollment(Long id,Enrollment enrollment){
        Enrollment old = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found id: " + id));
        old.setStudentName(enrollment.getStudentName());
        old.setCourseId(enrollment.getCourseId());

        return enrollmentRepository.save(old);
    }
    public boolean deleteEnrollmentById(Long id){
        Enrollment  enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found id: " + id));
        enrollmentRepository.delete(enrollment);
        return true;
    }
}
