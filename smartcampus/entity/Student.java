package com.smartcampus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String studentNumber;          // e.g. STU2026001

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phone;
    private String gender;
    private LocalDate dateOfBirth;
    private String address;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    private Integer yearOfStudy = 1;        // 1, 2, 3, 4...

    private String status = "ACTIVE";       // ACTIVE, SUSPENDED, GRADUATED
}
