package com.smartcampus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "enrollment_id", nullable = false, unique = true)
    private Enrollment enrollment;

    private Double courseworkMark;         // out of 40 (example)
    private Double examMark;               // out of 60 (example)
    private Double totalMark;              // calculated
    private String grade;                  // A, B, C, D, F
    private Double gradePoint;             // 5.0, 4.0, 3.0...

    private String remarks;
}
