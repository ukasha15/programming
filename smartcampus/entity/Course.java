package com.smartcampus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;                   // e.g. CS101

    @Column(nullable = false)
    private String title;

    private String description;
    private Integer creditUnits = 3;
    private Integer yearLevel = 1;         // Year 1, 2, 3...
    private String semester;               // SEMESTER_1, SEMESTER_2

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;             // Assigned lecturer
}
