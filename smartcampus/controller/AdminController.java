package com.smartcampus.controller;

import com.smartcampus.entity.Student;
import com.smartcampus.repository.ProgramRepository;
import com.smartcampus.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StudentService studentService;
    private final ProgramRepository programRepository;

    // ==================== DASHBOARD ====================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard");
        model.addAttribute("studentCount", studentService.count());
        return "admin/dashboard";
    }

    // ==================== STUDENT LIST ====================
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        model.addAttribute("pageTitle", "Manage Students");
        return "admin/students";
    }

    // ==================== SHOW CREATE FORM ====================
    @GetMapping("/students/new")
    public String showCreateForm(Model model) {
        model.addAttribute("programs", programRepository.findAll());
        model.addAttribute("pageTitle", "Add New Student");
        return "admin/student-form";
    }

    // ==================== SAVE NEW STUDENT ====================
    @PostMapping("/students")
    public String createStudent(
            @RequestParam String studentNumber,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) Integer yearOfStudy,
            RedirectAttributes redirectAttributes) {

        try {
            studentService.createStudent(studentNumber, firstName, lastName, email,
                    username, password, phone, gender, programId, yearOfStudy);
            redirectAttributes.addFlashAttribute("success", "Student created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/students/new";
        }
        return "redirect:/admin/students";
    }

    // ==================== SHOW EDIT FORM ====================
    @GetMapping("/students/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return studentService.findById(id)
                .map(student -> {
                    model.addAttribute("student", student);
                    model.addAttribute("programs", programRepository.findAll());
                    model.addAttribute("pageTitle", "Edit Student");
                    return "admin/student-edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Student not found");
                    return "redirect:/admin/students";
                });
    }

    // ==================== UPDATE STUDENT ====================
    @PostMapping("/students/update/{id}")
    public String updateStudent(
            @PathVariable Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Long programId,
            @RequestParam(required = false) Integer yearOfStudy,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {

        try {
            studentService.updateStudent(id, firstName, lastName, phone, gender, programId, yearOfStudy, status);
            redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }

    // ==================== DELETE STUDENT ====================
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }
}
