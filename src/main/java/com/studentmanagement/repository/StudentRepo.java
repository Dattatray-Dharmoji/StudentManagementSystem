package com.studentmanagement.repository;

import com.studentmanagement.model.Courses;
import com.studentmanagement.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Students, Long> {
boolean existsByEmailIgnoreCase(String email);
    Page<Students> findByActiveTrue(Pageable page);
    List<Students> findByActiveTrue();
    boolean existsByEmailAndIdNot(String email, Long id);


    @Query(
            value = """
        SELECT DISTINCT s
        FROM Students s
        JOIN s.enrollments e
        """,
            countQuery = """
        SELECT COUNT(DISTINCT s.id)
        FROM Students s
        JOIN s.enrollments e
        """
    )
    Page<Students> findEnrolledStudents(Pageable page);


    @Query("""
        SELECT DISTINCT s
        FROM Students s
        JOIN FETCH s.enrollments e
        JOIN FETCH e.course c
        WHERE s.id = :id
        """)
    Optional<Students> findEnrolledStudentCourseDetails(@Param("id") Long id);

}
