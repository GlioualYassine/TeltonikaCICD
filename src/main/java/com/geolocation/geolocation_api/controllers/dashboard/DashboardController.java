package com.geolocation.geolocation_api.controllers.dashboard;

import com.geolocation.geolocation_api.dto.DashboardResponseDTO;
import com.geolocation.geolocation_api.services.dashboard.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("summary")
    public ResponseEntity<DashboardResponseDTO> getSummary() {
        return ResponseEntity.ok(dashboardService.getCardSummary());
    }
}
