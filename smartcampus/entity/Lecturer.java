package com.smartcampus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String staffNumber;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String phone;
    private String title;                  // Dr, Prof, Mr, Mrs...

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
