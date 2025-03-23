package com.example.centralized_server.controller;

import com.example.centralized_server.dto.StatisticDto;
import com.example.centralized_server.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/statistic")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    public ResponseEntity<StatisticDto> getStatistic() {
        return ResponseEntity.ok(statisticService.getStatistics()) ;
    }
}
