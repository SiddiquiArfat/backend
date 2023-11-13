package com.revly.Revly.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doubt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String doubt;

    String subject;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    @ManyToOne
    Users user;

    @ManyToOne
    TutorAvailability tutorAvailability;
}
