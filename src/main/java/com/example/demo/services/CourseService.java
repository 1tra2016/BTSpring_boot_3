package com.example.demo.services;


import com.example.demo.models.Instructor;
import com.example.demo.models.Course;
import com.example.demo.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorService instructorService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
    }

    public List<Course> getAllCourses(String search) {
        List<Course> courses = courseRepository.findAll();
        if (search != null) {
            courses = courses
                    .stream()
                    .filter(u -> u.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .toList();
        }
        return courses;
    }

    //public Course findById(Long id){
    //    return courseRepository.findById(id).orElse(null);
    //}

    public Course  getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        return course;
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course newCourse) {
        Course old = courseRepository.findById(id).orElse(null);
        if (old == null) return null;

        old.setTitle(newCourse.getTitle());
        old.setStatus(newCourse.getStatus());
        old.setInstructorId(newCourse.getInstructorId());

        return courseRepository.save(old);
    }

    public boolean deleteCourse(Long id){
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) return false;
        courseRepository.deleteById(id);
        return true;
    }
}
