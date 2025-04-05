package com.example.centralized_server.controller;

import com.example.centralized_server.dto.StatisticDto;
import com.example.centralized_server.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/statistic")
@AllArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<StatisticDto> getStatistic() {
        return ResponseEntity.ok(statisticService.getStatistics());
    }

    @GetMapping("/count-by-month")
    public ResponseEntity<List<Integer>> countOrdersByMonth(
            @RequestParam String verifyAddress,
            @RequestParam int year) {

        List<Integer> orderCounts = statisticService.countOrdersByMonth(verifyAddress, year);

        return ResponseEntity.ok(orderCounts);
    }

    @GetMapping("/count-last-7-days")
    public ResponseEntity<Map<String, Object>> getLast7DaysStatistics(
            @RequestParam String verifyAddress) {

        Map<String, Object> last7DaysData = statisticService.getLast7DaysOrderCounts(verifyAddress);
        return ResponseEntity.ok(last7DaysData);
    }

    @GetMapping("/count-all-transferred")
    public long countTransferredOrders() {
        return statisticService.countTransferredOrders();
    }

    @GetMapping("/count-non-all-transferred")
    public long countNonTransferredOrders() {
        return statisticService.countNonTransferredOrders();
    }

    @GetMapping("/count-transferred")
    public long countTransferredOrdersByVerifier(@RequestParam String verifyAddress) {
        return statisticService.countTransferredOrdersByVerifier(verifyAddress);
    }

    @GetMapping("/count-non-transferred")
    public long countNonTransferredOrdersByVerifier(@RequestParam String verifyAddress) {
        return statisticService.countNonTransferredOrdersByVerifier(verifyAddress);
    }

    @GetMapping("/count-by-role")
    public Map<String, Long> countUsersByRole() {
        return statisticService.countUsersByRole();
    }

    @GetMapping("/count-by-week")
    public Map<String, Map<String, Long>> getUserAndOrderCountByWeek() {
        return statisticService.getUsersAndOrdersByWeek();
    }
}
