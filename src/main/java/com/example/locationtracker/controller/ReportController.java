package com.example.locationtracker.controller;

import com.example.locationtracker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/report/totalDistance")
    public String getTotalDistance() {
        double distance = reportService.calculateTotalDistance();
        return "Total distance traveled: " + distance + " km";
    }
}
