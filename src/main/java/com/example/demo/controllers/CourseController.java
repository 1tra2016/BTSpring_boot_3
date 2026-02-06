package com.example.demo.controllers;

import com.example.demo.models.Course;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String search) {
        List<Course> courses = courseService.getAllCourses();
        if (search != null && !search.isEmpty()) {
            courses = courses.stream()
                    .filter(u -> u.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if(course == null) return ResponseEntity.notFound().build();
        else return  ResponseEntity.ok(course);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course newCourse) {
        Course createdCourse = courseService.createCourse(newCourse);

        if (createdCourse == null) {
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCourse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable long id,
            @RequestBody Course newCourse) {

        Course course = courseService.updateCourse(id, newCourse);
        if(course ==null){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(course);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable long id) {
        if(courseService.deleteCourse(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
