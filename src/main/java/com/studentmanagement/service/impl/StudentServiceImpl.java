package com.studentmanagement.service.impl;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.dto.StudentsDTO;

import com.studentmanagement.model.Courses;
import com.studentmanagement.model.Students;
import com.studentmanagement.repository.StudentRepo;
import com.studentmanagement.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final StudentRepo studentRepo;
    private final ModelMapper mapper;

    StudentServiceImpl(StudentRepo StudentRepo , ModelMapper mapper) {
        this.studentRepo =StudentRepo ;
        this.mapper = mapper;

    }


    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("email from create student ");
        return studentRepo.existsByEmailIgnoreCase(email);
    }// this function is used authenthicate that user come with diffrent email else it makes complications

    @Override
    public StudentsDTO CreateStudent(StudentsDTO studentsDTO) {
        log.info("create student ");
       Students students = this.mapper.map(studentsDTO, Students.class);
       Students saved =studentRepo.save(students);

        log.info("Students Saved");
        return mapper.map(saved, StudentsDTO.class);




    }
    @Override
    public Page<StudentsDTO> getStudents(int page, int size) {

        ;  PageRequest Page=  PageRequest.of(page, size , Sort.by(Sort.Direction.DESC, "id"));
        return    studentRepo.findByActiveTrue(Page)
                .map(students -> mapper.map(students, StudentsDTO.class));
    }



    // Student view logic will be returned here

    @Override
    @Transactional(readOnly = true)
    public StudentsDTO getStudentById(Long id) {

        Students student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return mapper.map(student, StudentsDTO.class);
    }

    // Update student Logic i was going to write here

    @Override
    public StudentsDTO updateStudent(Long id, StudentsDTO studentsDTO) {
        Students student = studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));


        mapper.map(studentsDTO, student) ;
        Students updated=studentRepo.save(student);

        return  mapper.map(updated, StudentsDTO.class);

    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return studentRepo.existsByEmailAndIdNot(email, id);
    }


    @Override
    public List<StudentsDTO> getAllStudents() {
        return studentRepo.findByActiveTrue().stream()
                .map(student -> mapper.map(student, StudentsDTO.class))
                .collect(Collectors.toList());
    }



}
