
package com.studentmanagement.controller;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.exception.GlobalExceptionHandler;
import com.studentmanagement.service.CourseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {

    private static final Logger log =
            LoggerFactory.getLogger(CourseController.class);



    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/new")
    public String showCreateCourse(Model model) {
        log.info("/ new  get method called under CourseController");
        model.addAttribute("CourseDTO", new CourseDTO());
        return "add-course";
    }


    // pagination and All course List LOgic Returned Here !


    @GetMapping("/list")
    public String listCourses(@RequestParam (defaultValue = "0")int page ,
                              @RequestParam (defaultValue = "5")int size ,
                              Model model) {
        log.info("/ list  Showing course list  page using  Get mapping ");
      Page <CourseDTO> Courses= courseService.getCourses(page , size);
      model.addAttribute("Courses", Courses);
        return "courses";
    }

    @PostMapping("/save")


    public String CreateCourses(
            @Valid @ModelAttribute("CourseDTO") CourseDTO courseDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        log.info("/ Save  post method called under CourseController where post request is received");

        if (bindingResult.hasErrors()) {


            log.error("page redirect to add-course due to validation errors");
            return "add-course";
        }

        if (courseService.existsByCourseCode(courseDTO.getCourseCode())) {

            log.error("page redirect to add-course due to validation errors course code already exists");
            bindingResult.rejectValue(

                    "courseCode",
                    "error.courseCode",
                    "Code Must be unique"
            );
            return "add-course";
        }


        courseService.CreateCourse(courseDTO);

        redirectAttributes.addFlashAttribute(
                "message",
                "Course is created successfully"
        );


        log.info("course saved successfully");

        return "redirect:/course/list";

    }
// View course eye button working controller right here !!
    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id , Model model) {
 CourseDTO course = courseService.getCourseById(id);
model.addAttribute("course", course);
 return "view-course";
    }


    // edit controller right here for course edit icon working
    @GetMapping("/{id}/edit")
    public String EditCourse(@PathVariable Long id , Model model) {
 CourseDTO course = courseService.getCourseById(id);
model.addAttribute("CourseDTO", course);
 return "edit-course";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id,  @Valid @ModelAttribute("CourseDTO") CourseDTO courseDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes){

        log.info("/ Save  post method called under CourseController where post request is received");

        if (bindingResult.hasErrors()) {


            log.error("page redirect to edit-course due to validation errors");
            return "edit-course";
        }

        if (courseService.existsByCourseCodeAndIdNot(courseDTO.getCourseCode(),id)) {

            log.error("page redirect to edit-course due to validation errors course code already exists");
            bindingResult.rejectValue(

                    "courseCode",
                    "error.courseCode",
                    "Code Must be unique"
            );
            return "edit-course";
        }


        courseService.updateCourse(id ,courseDTO);

        redirectAttributes.addFlashAttribute(
                "message",
                "Course is Updated successfully"
        );


        log.info("updated course successfully");

        return "redirect:/course/list";




    }



}
