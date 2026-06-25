package com.gatepass.guardhouse.visitor.dto;

import com.gatepass.guardhouse.visitor.model.VisitorType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegisterVisitorRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String icOrPassport;

    @NotBlank
    private String phone;

    @NotNull
    private VisitorType type;

    @NotBlank
    private String hostResidentId;

    @NotBlank
    private String purpose;

    @NotNull
    @Future
    private LocalDate expectedDate;
}
