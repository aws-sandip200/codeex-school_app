package com.skoolbus.controller;

import com.skoolbus.dto.DashboardResponse;
import com.skoolbus.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/guardian/{guardianId}")
    public DashboardResponse guardianDashboard(@PathVariable Long guardianId) {
        return dashboardService.guardianDashboard(guardianId);
    }
}
