package com.smartcampus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;       // PRESENT, ABSENT, LATE, EXCUSED

    private String remarks;
}

enum AttendanceStatus {
    PRESENT, ABSENT, LATE, EXCUSED
}
