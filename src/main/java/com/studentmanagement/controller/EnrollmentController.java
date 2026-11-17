package com.studentmanagement.controller;


import com.studentmanagement.dto.EnrollmentDTO;
import com.studentmanagement.dto.EnrollmentSummaryDTO;
import com.studentmanagement.dto.StudentsDTO;
import com.studentmanagement.service.CourseService;
import com.studentmanagement.service.EnrollmentService;
import com.studentmanagement.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;


    public EnrollmentController(CourseService courseService, StudentService studentService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }


    private static final Logger log = LoggerFactory.getLogger(EnrollmentController.class);

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){
        log.info("Showing  enroll course page ");
        model.addAttribute("enrollmentDto", new EnrollmentDTO());
        model.addAttribute("courseList", courseService.getAllCourses());
        model.addAttribute("studentList", studentService.getAllStudents());
        return "enroll-course";
    }


    // Save Student Enrollment
    @PostMapping("/assign")
    public String assignCourses(
            @ModelAttribute("enrollmentDto") EnrollmentDTO enrollmentDTO,
            RedirectAttributes redirectAttributes) {

        try {

            enrollmentService.assignCourses(enrollmentDTO);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Student enrolled successfully!"
            );

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );

        }

        return "redirect:/enrollments/showEnroll";
    }



    @GetMapping ("/enrollmentList")
    public String enrollmentList( @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "3") int size,
                                  Model model
    ) {

        log.info("list student method called under Enrollment Controller showing enrolled students list page {}", page);

        Page<EnrollmentSummaryDTO> students = enrollmentService.getEnrolledStudents(page, size);

        model.addAttribute("students", students);

        return "enrolled-students";
    }

    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id, Model model,
                                              @RequestParam(defaultValue = "enrollments") String source) {

        EnrollmentSummaryDTO enrollmentSummaryDTO
                = enrollmentService.findEnrolledStudentCourseDetails(id);

        model.addAttribute("enrollmentSummaryDTO", enrollmentSummaryDTO);
        model.addAttribute("source", source);

        return "enrollment-details";

    }






}
