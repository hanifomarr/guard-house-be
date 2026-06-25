package com.gatepass.guardhouse.visitor.dto;

import com.gatepass.guardhouse.visitor.model.Visitor;
import com.gatepass.guardhouse.visitor.model.VisitorStatus;
import com.gatepass.guardhouse.visitor.model.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorResponse {

    private String id;
    private String name;
    private String icOrPassport;
    private String phone;
    private VisitorType type;
    private String registeredBy;
    private String hostResidentId;
    private String purpose;
    private LocalDate expectedDate;
    private VisitorStatus status;
    private String qrToken;
    private String qrCode;
    private Instant qrExpiry;

    public static VisitorResponse from(Visitor visitor) {
        return VisitorResponse.builder()
                .id(visitor.getId())
                .name(visitor.getName())
                .icOrPassport(visitor.getIcOrPassport())
                .phone(visitor.getPhone())
                .type(visitor.getType())
                .registeredBy(visitor.getRegisteredBy())
                .hostResidentId(visitor.getHostResidentId())
                .purpose(visitor.getPurpose())
                .expectedDate(visitor.getExpectedDate())
                .status(visitor.getStatus())
                .qrToken(visitor.getQrToken())
                .qrCode(visitor.getQrCode())
                .qrExpiry(visitor.getQrExpiry())
                .build();
    }
}
