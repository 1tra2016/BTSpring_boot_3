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
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses(@RequestParam(required = false) String search) {
        List<Course> courses = courseService.getAllCourses(search);
        return ResponseEntity.ok(ApiResponse.success("Thành công", courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        try {
            return ResponseEntity.ok(ApiResponse.success("Thành công", course));
        }catch(RuntimeException e) {
            return  ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course newCourse) {
        Course course = courseService.createCourse(newCourse);
        try {
            return ResponseEntity.ok(ApiResponse.success("Thành công", course));
        }catch(RuntimeException e) {
            return  ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(
            @PathVariable long id,
            @RequestBody Course newCourse
    ) {
        Course course = courseService.updateCourse(id, newCourse);
        try {
            return ResponseEntity.ok(ApiResponse.success("Thành công", course));
        }catch(RuntimeException e) {
            return  ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> deleteCourse(@PathVariable long id) {
        courseService.deleteCourse(id);
        try {
            return ResponseEntity.ok(ApiResponse.success("Thành công", null));
        }catch(RuntimeException e) {
            return  ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }
}
