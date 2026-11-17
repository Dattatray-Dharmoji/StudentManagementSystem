package com.studentmanagement.service.impl;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.dto.EnrollmentDTO;
import com.studentmanagement.dto.EnrollmentSummaryDTO;
import com.studentmanagement.dto.StudentsDTO;
import com.studentmanagement.model.Courses;
import com.studentmanagement.model.Enrollment;
import com.studentmanagement.model.Students;
import com.studentmanagement.repository.CourseRepo;
import com.studentmanagement.repository.EnrollmentRepo;
import com.studentmanagement.repository.StudentRepo;
import com.studentmanagement.service.EnrollmentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepo enrollmentRepo;
    private final StudentRepo studentsRepo;
    private final CourseRepo coursesRepo;
    private final ModelMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    public EnrollmentServiceImpl(EnrollmentRepo enrollmentRepo, StudentRepo studentsRepo, CourseRepo coursesRepo, ModelMapper mapper) {
        this.enrollmentRepo = enrollmentRepo;
        this.studentsRepo = studentsRepo;
        this.coursesRepo = coursesRepo;
        this.mapper = mapper;
    }



    public void assignCourses(EnrollmentDTO enrollmentDTO) {

        // 1. Find student using student ID
        Students student = studentsRepo.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2. Loop through selected course IDs
        for (Long courseId : enrollmentDTO.getCourseIds()) {

            // 3. Find course using course ID
            Courses course = coursesRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));



            // 4. Check duplicate enrollment
            if (enrollmentRepo.existsByStudent_IdAndCourse_Id(
                    student.getId(), course.getId()))
            {
                throw new RuntimeException("Student already enrolled in this course!");
            }




            // 5. Create enrollment object
            Enrollment enrollment = new Enrollment();

            // 6. Assign student and course
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            // 7. Save enrollment
            enrollmentRepo.save(enrollment);
        }




    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size) {
        log.info("List of enrolled students from {}", page);
        PageRequest Page=  PageRequest.of(page, size , Sort.by(Sort.Direction.DESC, "id"));
        return   studentsRepo.findEnrolledStudents(Page)
                .map(students -> {
                    EnrollmentSummaryDTO dto= new EnrollmentSummaryDTO();
                    dto.setStudentId(students.getId());
                    dto.setStudentName(students.getFirstname()+ " " + students.getLastname());
                    dto.setStudentEmail(students.getEmail());

                    dto.setCourseCount(students.getEnrollments().size());
                    BigDecimal totalFee=students.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);
                    return dto;
                });
    }

    @Override
    public EnrollmentSummaryDTO findEnrolledStudentCourseDetails(Long studentId) {
        return studentsRepo.findEnrolledStudentCourseDetails(studentId)
                .map(student -> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstname() + " "+student.getLastname());
                    dto.setStudentEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDTO> courseList = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(course -> mapper.map(course, CourseDTO.class))
                            .collect(Collectors.toList());

                    dto.setCourseList(courseList);

                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));




    }





    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents() {
        log.info("list of recently enrolled students");

        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));
        return studentsRepo.findEnrolledStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstname() + " "+student.getLastname());
                    dto.setStudentEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    return dto;
                })
                .getContent();




}
}
