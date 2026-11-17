package com.studentmanagement.repository;

import com.studentmanagement.dto.EnrollmentDTO;
import com.studentmanagement.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface EnrollmentRepo extends JpaRepository<Enrollment , Long> {

    boolean existsByStudent_IdAndCourse_Id(
            Long studentId,
            Long courseId
    );


    @Query("""
			select count(distinct e.student.id) from 
			Enrollment e 
			where e.enrolledDate between :startDate and :endDate
			""")
    long countDistinctStudentByEnrollDateBetween(@Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);




}
