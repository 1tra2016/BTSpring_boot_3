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
        if(course == null) return ResponseEntity.notFound().build();
        else return  ResponseEntity.ok(ApiResponse.success("Thành công", course));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course newCourse) {
        Course course = courseService.createCourse(newCourse);

        if (course == null) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(ApiResponse.success("Thành công", course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(
            @PathVariable long id,
            @RequestBody Course newCourse) {

        Course course = courseService.updateCourse(id, newCourse);
        if(course ==null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Course not found"));
        else return ResponseEntity.ok(ApiResponse.success("Thành công", course));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> deleteCourse(@PathVariable long id) {
        if(courseService.deleteCourse(id)) return ResponseEntity.ok(ApiResponse.success("Xóa thành công",null));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Course not found"));
    }
}
