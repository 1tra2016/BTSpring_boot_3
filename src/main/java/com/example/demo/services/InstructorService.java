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

    public List<Instructor> getAllInstructor() {
        return instructorRepository.findAll();
    }
    public Instructor getInstructorById(Long id){return instructorRepository.findById(id).orElse(null);}
//    public Instructor findInstructorById(Long id){
//        return instructorRepository.findById(id).orElse(null);
//    }
    public Instructor updateInstructor(Long id, Instructor newInstructor) {
        Instructor old = instructorRepository.findById(id).orElse(null);
        if(old == null) return null;
        old.setInstructorname(newInstructor.getInstructorname());
        old.setEmail(newInstructor.getEmail());
        instructorRepository.save(old);
        return old;
    }
    public boolean deleteInstructorById(Long id){
        Instructor instructor  = instructorRepository.findById(id).orElse(null);
        if(instructor == null) return false;
        instructorRepository.delete(instructor);
        return true;
    }
}

