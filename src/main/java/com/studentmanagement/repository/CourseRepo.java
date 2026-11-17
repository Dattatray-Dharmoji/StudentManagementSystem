package com.studentmanagement.repository;

import com.studentmanagement.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Courses, Long> {
    boolean existsByCourseCodeIgnoreCase(String code);

boolean existsByCourseCodeAndIdNot(String code, Long id );
  Page<Courses> findByActiveTrue(Pageable page);
  List<Courses> findByActiveTrue(Sort sort);

}


