package com.solution.hacktrail.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkRateDTO {

    private Long id;
    private LocalDate date;
    private int workedHours;
    private int qualityOfWork;
    private int focusAndConcentration;
    private int feelingOfAccomplishment;
    private int energyLevels;
    private String strugglePoint;
    private double overallRating;
}
