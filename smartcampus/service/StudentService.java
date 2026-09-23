package com.smartcampus.service;

import com.smartcampus.entity.*;
import com.smartcampus.repository.ProgramRepository;
import com.smartcampus.repository.StudentRepository;
import com.smartcampus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public long count() {
        return studentRepository.count();
    }

    @Transactional
    public Student createStudent(String studentNumber, String firstName, String lastName,
                                 String email, String username, String rawPassword,
                                 String phone, String gender, Long programId, Integer yearOfStudy) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        if (studentRepository.findByStudentNumber(studentNumber).isPresent()) {
            throw new RuntimeException("Student number already exists");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .email(email)
                .role(Role.STUDENT)
                .enabled(true)
                .build();
        userRepository.save(user);

        Program program = null;
        if (programId != null) {
            program = programRepository.findById(programId).orElse(null);
        }

        Student student = Student.builder()
                .studentNumber(studentNumber)
                .user(user)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .gender(gender)
                .program(program)
                .yearOfStudy(yearOfStudy != null ? yearOfStudy : 1)
                .status("ACTIVE")
                .build();

        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long id, String firstName, String lastName, String phone,
                                 String gender, Long programId, Integer yearOfStudy, String status) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPhone(phone);
        student.setGender(gender);
        student.setYearOfStudy(yearOfStudy);
        student.setStatus(status);

        if (programId != null) {
            programRepository.findById(programId).ifPresent(student::setProgram);
        }

        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        User user = student.getUser();
        studentRepository.delete(student);
        userRepository.delete(user);
    }
}
