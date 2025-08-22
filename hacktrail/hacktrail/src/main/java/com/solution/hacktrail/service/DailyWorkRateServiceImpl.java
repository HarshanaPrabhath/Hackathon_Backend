package com.solution.hacktrail.service;


import com.solution.hacktrail.model.DailyWorkRate;
import com.solution.hacktrail.model.User;
import com.solution.hacktrail.payload.DailyWorkRateDTO;
import com.solution.hacktrail.repositories.DailyWorkRateRepository;
import com.solution.hacktrail.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class DailyWorkRateServiceImpl implements DailyWorkRateService {

    private final DailyWorkRateRepository dailyWorkRateRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public String dayRate(DailyWorkRateDTO dto) {

        // Map DTO to entity
        DailyWorkRate dailyWorkRate = modelMapper.map(dto, DailyWorkRate.class);


        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Quantitative score using worked hours
        double quantitativeScore = dailyWorkRate.getWorkedHours();

        // Qualitative score: average of 4 self-reported fields
        double qualitativeScore = (
                dailyWorkRate.getQualityOfWork() +
                        dailyWorkRate.getFocusAndConcentration() +
                        dailyWorkRate.getFeelingOfAccomplishment() +
                        dailyWorkRate.getEnergyLevels()
        ) / 4.0;

        // Normalize quantitative score to 1-5 scale (assuming 12 max work hours)
        double normalizedQuantitative = Math.min(quantitativeScore / 12.0 * 5, 5);

        // Combine quantitative and qualitative scores (weighted)
        double overallRating = 0.4 * normalizedQuantitative + 0.6 * qualitativeScore;
        overallRating = Math.round(overallRating * 100.0) / 100.0;

        // Set values

        dailyWorkRate.setOverallRating(overallRating);
        dailyWorkRate.setDate(LocalDate.now());
        dailyWorkRate.setUser(user);

        // Save entity
        dailyWorkRateRepository.save(dailyWorkRate);

        return "Daily work rate saved successfully with overall rating: " + overallRating;
    }
}

