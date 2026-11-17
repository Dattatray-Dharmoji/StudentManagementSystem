package com.studentmanagement.service;

import com.studentmanagement.dto.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    CourseDTO CreateCourse(CourseDTO courseDTO);


    boolean existsByCourseCode(String code);


    Page<CourseDTO> getCourses(int page , int size);

    CourseDTO getCourseById(Long id);

    CourseDTO updateCourse(Long id , CourseDTO courseDTO);
    boolean existsByCourseCodeAndIdNot(String code , Long id);
List<CourseDTO> getAllCourses();
}
