package com.smartcampus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;                   // e.g. Bachelor of Computer Science

    private String code;                   // e.g. BCS
    private Integer durationYears = 4;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
