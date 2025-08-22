package com.solution.hacktrail.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private LocalDate date;
    private int workedHours;
    private int qualityOfWork;
    private int focusAndConcentration;
    private int feelingOfAccomplishment;
    private int energyLevels;
    private String strugglePoint;

    private double overallRating;

}
