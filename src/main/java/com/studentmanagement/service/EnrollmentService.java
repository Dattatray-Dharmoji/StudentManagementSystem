package com.studentmanagement.service;

import com.studentmanagement.dto.EnrollmentDTO;
import com.studentmanagement.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {

        void assignCourses(EnrollmentDTO enrollmentDTO);

        Page<EnrollmentSummaryDTO> getEnrolledStudents(int page , int size);
        EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId);
        List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents();
}
