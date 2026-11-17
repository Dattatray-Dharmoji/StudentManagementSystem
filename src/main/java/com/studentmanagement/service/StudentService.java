package com.studentmanagement.service;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.dto.EnrollmentSummaryDTO;
import com.studentmanagement.dto.StudentsDTO;
import com.studentmanagement.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    boolean existsByEmailIgnoreCase(String email);
StudentsDTO CreateStudent(StudentsDTO studentsDTO);

    StudentsDTO getStudentById(Long id);
    Page<StudentsDTO> getStudents(int page, int size);

    StudentsDTO updateStudent(Long id , StudentsDTO studentsDTO);
    boolean existsByEmailAndIdNot(String email, Long id);
    List<StudentsDTO> getAllStudents();

}
