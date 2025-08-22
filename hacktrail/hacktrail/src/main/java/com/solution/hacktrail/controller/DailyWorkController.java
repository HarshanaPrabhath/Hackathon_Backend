package com.solution.hacktrail.controller;


import com.solution.hacktrail.payload.DailyWorkRateDTO;
import com.solution.hacktrail.service.DailyWorkRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class DailyWorkController {

    private final DailyWorkRateService dailyWorkRateService;

    @PostMapping("/day-rate")
    public ResponseEntity<String> dayRate(@RequestBody  DailyWorkRateDTO dailyWorkRateDTO) {

        String response = dailyWorkRateService.dayRate(dailyWorkRateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
