package com.gatepass.guardhouse.visitlog.dto;

import com.gatepass.guardhouse.visitlog.model.EntryMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogEntryRequest {

    @NotBlank
    private String visitorId;

    @NotNull
    private EntryMethod entryMethod;

    private String notes;
}