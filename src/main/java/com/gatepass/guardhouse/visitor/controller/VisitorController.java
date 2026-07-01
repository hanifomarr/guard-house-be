package com.gatepass.guardhouse.visitor.controller;

import com.gatepass.guardhouse.auth.security.GuardhouseUserDetails;
import com.gatepass.guardhouse.common.response.ApiResponse;
import com.gatepass.guardhouse.visitor.dto.RegisterVisitorRequest;
import com.gatepass.guardhouse.visitor.dto.VisitorResponse;
import com.gatepass.guardhouse.visitor.service.VisitorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
public class VisitorController {

    private final VisitorService visitorService;

    @PreAuthorize("hasAnyRole('RESIDENT','GUARD')")
    @PostMapping
    public ResponseEntity<ApiResponse<VisitorResponse>> registerVisitor(
            @RequestBody @Valid RegisterVisitorRequest request,
            @AuthenticationPrincipal GuardhouseUserDetails userDetails) {

        String userId = userDetails.getUserId();
        VisitorResponse response = visitorService.registerVisitor(request, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Visitor registered successfully", response));
    }

    @PreAuthorize("hasAnyRole('RESIDENT', 'GUARD', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VisitorResponse>> getVisitorById(
            @PathVariable("id") String id) {

        VisitorResponse response = visitorService.getVisitorById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Visitor retrieved successfully", response));
    }

    @PreAuthorize("hasAnyRole('RESIDENT', 'GUARD', 'ADMIN')")
    @GetMapping("/{id}/qr")
    public ResponseEntity<byte[]> getVisitorQrCode(
            @PathVariable("id") String id) {

        byte[] visitorQrCode = visitorService.getVisitorQrCode(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                .body(visitorQrCode);
    }

    @PreAuthorize("hasRole('RESIDENT')")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<VisitorResponse>>> getVisitorsByHostResidentId(
            @AuthenticationPrincipal GuardhouseUserDetails userDetails
    ) {
        String userId = userDetails.getUserId();
        List<VisitorResponse> visitors = visitorService.getVisitorsByHostResidentId(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Visitors retrieved successfully", visitors));
    }
}
