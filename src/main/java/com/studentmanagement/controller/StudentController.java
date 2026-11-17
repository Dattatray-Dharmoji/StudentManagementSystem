package com.studentmanagement.controller;


import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.dto.StudentsDTO;
import com.studentmanagement.service.StudentService;
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
@RequestMapping("/students")
public class StudentController {
    private static final Logger log =
            LoggerFactory.getLogger(StudentController.class);
private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

//pagination and student List logic will be returned here

    @GetMapping("/list")
    public String listStudent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        log.info("list student method called under StudentController");

        Page<StudentsDTO> students = studentService.getStudents(page, size);

        model.addAttribute("students", students);

        return "students";
    }




// opening add student form logic will be returned here
@GetMapping ("/new")
   public String CreateStudent(Model model){
        model.addAttribute("studentsDTO" , new StudentsDTO());
        return "add-student";
   }

   // saving student form data in databse
    @PostMapping("/save")
    public String CreateStudent(@Valid  @ModelAttribute StudentsDTO studentsDTO,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes) {

log.info("CreateStudent method called under StudentController where post request is received");
if (bindingResult.hasErrors()) {
    return "add-student";

}
if (studentService.existsByEmailIgnoreCase(studentsDTO.getEmail())) {
    log.error("email must be unique");
bindingResult.rejectValue("email", "null", "Email must be unique");
    return "add-student";
}
studentService.CreateStudent(studentsDTO);
        redirectAttributes.addFlashAttribute(
                "message",
                "Student is created successfully");





        return "redirect:/students/list";





    }


    //now making view course icon working

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id , Model model) {
       StudentsDTO student  = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "view-student";
    }
// now i am making edit student icon working controller right here


    @GetMapping("/{id}/edit")
    public String EditStudent(@PathVariable Long id , Model model) {
        StudentsDTO student = studentService.getStudentById(id);
        model.addAttribute("studentsDTO", student);
        return "edit-student";
    }



    // UPdate student details and saving databse  controller right here


    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id,  @Valid @ModelAttribute("studentsDTO") StudentsDTO studentsDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

        log.info("/ Save  post method called under StudentController where post request is received");

        if (bindingResult.hasErrors()) {


            log.error("page redirect to edit-student due to validation errors");
            return "edit-student";
        }

        if (studentService.existsByEmailAndIdNot(studentsDTO.getEmail(), id)) {

            log.error("page redirect to edit-student due to validation errors email already exists");
            bindingResult.rejectValue(

                    "email",
                    "error.email",
                    "Email Must be unique"
            );
            return "edit-student";
        }


        studentService.updateStudent(id ,studentsDTO);

        redirectAttributes.addFlashAttribute(
                "message",
                "Student is Updated successfully"
        );


        log.info("updated student successfully");

        return "redirect:/students/list";




    }








}
