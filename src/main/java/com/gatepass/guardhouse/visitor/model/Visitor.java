package com.gatepass.guardhouse.visitor.model;

import com.gatepass.guardhouse.common.model.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection = "visitors")
public class Visitor extends BaseDocument {

    private String name;

    private String icOrPassport;

    private String phone;

    private VisitorType type;

    private String registeredBy;

    private String hostResidentId;

    private String purpose;

    private LocalDate expectedDate;

    private VisitorStatus status = VisitorStatus.PENDING;

    private String qrToken;

    private String qrCode;

    private Instant qrExpiry;

}

