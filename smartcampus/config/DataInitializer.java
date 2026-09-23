package com.smartcampus.config;

import com.smartcampus.entity.*;
import com.smartcampus.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ProgramRepository programRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create default admin if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@smartcampus.edu")
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);
            System.out.println(">>> Default ADMIN created: username=admin / password=admin123");
        }

        // Sample department & program
        if (departmentRepository.count() == 0) {
            Department cs = Department.builder()
                    .name("Computer Science")
                    .code("CS")
                    .description("Department of Computer Science")
                    .build();
            departmentRepository.save(cs);

            Program bcs = Program.builder()
                    .name("Bachelor of Computer Science")
                    .code("BCS")
                    .durationYears(4)
                    .department(cs)
                    .build();
            programRepository.save(bcs);

            System.out.println(">>> Sample Department & Program created");
        }
    }
}
