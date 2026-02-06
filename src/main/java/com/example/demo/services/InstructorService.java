package com.example.demo.services;

import com.example.demo.models.Instructor;
import com.example.demo.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructor(String search) {
        List<Instructor> instructors = instructorRepository.findAll();
        if (search != null && !search.isEmpty()) {
            instructors = instructors
                    .stream()
                    .filter(u -> u.getInstructorname().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }
        return instructors;
    }
    public Instructor getInstructorById(Long id){
        return instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found id: " + id));
    }
//    public Instructor findInstructorById(Long id){
//        return instructorRepository.findById(id).orElse(null);
//    }
    public Instructor updateInstructor(Long id, Instructor newInstructor) {
        Instructor old = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found id: " + id));
        old.setInstructorname(newInstructor.getInstructorname());
        old.setEmail(newInstructor.getEmail());
        instructorRepository.save(old);
        return old;
    }
    public boolean deleteInstructorById(Long id){
        Instructor instructor  = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found id: " + id));
        instructorRepository.delete(instructor);
        return true;
    }
}

