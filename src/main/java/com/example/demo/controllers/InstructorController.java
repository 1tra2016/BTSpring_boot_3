package com.example.demo.controllers;

import com.example.demo.models.Instructor;
import com.example.demo.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructor(@RequestParam(required = false) String search) {
        List<Instructor> instructors = instructorService.getAllInstructor(search);

        return ResponseEntity.ok(instructors);
    }

    @GetMapping("{id}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable long id) {
        Instructor instructor = instructorService.getInstructorById(id);
        if(instructor == null) return ResponseEntity.notFound().build();
        else return  ResponseEntity.ok(instructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long id, @RequestBody Instructor newInstructor) {
        Instructor updatedInstructor = instructorService.updateInstructor(id, newInstructor);
        if(updatedInstructor == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(updatedInstructor);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Instructor>  deleteInstructorById(@PathVariable Long id) {
        if(instructorService.deleteInstructorById(id)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
