package com.gatepass.guardhouse.visitlog.controller;

import com.gatepass.guardhouse.auth.security.GuardhouseUserDetails;
import com.gatepass.guardhouse.common.response.ApiResponse;
import com.gatepass.guardhouse.visitlog.dto.LogEntryRequest;
import com.gatepass.guardhouse.visitlog.dto.LogExitRequest;
import com.gatepass.guardhouse.visitlog.dto.VisitLogResponse;
import com.gatepass.guardhouse.visitlog.service.VisitLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class VisitLogController {

    private final VisitLogService visitLogService;

    @PreAuthorize("hasRole('GUARD')")
    @PostMapping("/entry")
    public ResponseEntity<ApiResponse<VisitLogResponse>> logEntry(
            @RequestBody @Valid LogEntryRequest request,
            @AuthenticationPrincipal GuardhouseUserDetails userDetails) {

        String userId = userDetails.getUserId();
        VisitLogResponse response = visitLogService.logEntry(request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Entry log created successfully", response));
    }

    @PreAuthorize("hasRole('GUARD')")
    @PostMapping("/{id}/exit")
    public ResponseEntity<ApiResponse<VisitLogResponse>> logExit(
            @PathVariable("id") String id,
            @RequestBody @Valid LogExitRequest request) {

        VisitLogResponse response = visitLogService.logExit(request, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Exit logged successfully", response));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GUARD')")
    @GetMapping("/visitor/{visitorId}")
    public ResponseEntity<ApiResponse<List<VisitLogResponse>>> getLogsByVisitor(
            @PathVariable("visitorId") String visitorId) {

        List<VisitLogResponse> response = visitLogService.getLogsByVisitor(visitorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Visitor logs retrieved successfully", response));
    }
}