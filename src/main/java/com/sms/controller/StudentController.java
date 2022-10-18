package com.sms.controller;

import com.sms.entity.Student;
import com.sms.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        super();
        this.studentService = studentService;
    }

    // handler method to handle list students and return mode and view:
    @GetMapping("/students")
    public String listStudent(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    // handler method to add student:
    @GetMapping("/students/new")
    public String createStudentForm(Model model) {

        // create student object to hold student form data:
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }


    // handler method to Update the student:
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {

        // get student from database by id:
        Student existingStudent = studentService.getStudentById(id);

        // Now update the student:
        existingStudent.setId(student.getId());
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());

        // Now Updated student object, store into database:
        studentService.updateStudent(existingStudent);
        return "redirect:/students";

    }

    // handler method to handle delete student request:
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";

    }
}
