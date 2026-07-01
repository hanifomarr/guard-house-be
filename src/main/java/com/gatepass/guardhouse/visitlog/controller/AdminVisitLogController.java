package com.gatepass.guardhouse.visitlog.controller;

import com.gatepass.guardhouse.common.response.ApiResponse;
import com.gatepass.guardhouse.visitlog.dto.VisitLogResponse;
import com.gatepass.guardhouse.visitlog.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/logs")
@RequiredArgsConstructor
public class AdminVisitLogController {

    private final VisitLogService visitLogService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<VisitLogResponse>>> getAllLogs() {

        List<VisitLogResponse> response = visitLogService.getAllLogs();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("All logs retrieved successfully", response));
    }
}