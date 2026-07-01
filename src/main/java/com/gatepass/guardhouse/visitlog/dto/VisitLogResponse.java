package com.gatepass.guardhouse.visitlog.dto;

import com.gatepass.guardhouse.visitlog.model.EntryMethod;
import com.gatepass.guardhouse.visitlog.model.VisitLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitLogResponse {

    private String id;
    private String visitorId;
    private String residentId;
    private String guardId;
    private Instant entryTime;
    private Instant exitTime;
    private EntryMethod entryMethod;
    private String notes;

    public static VisitLogResponse from(VisitLog visitLog) {
        return VisitLogResponse.builder()
                .id(visitLog.getId())
                .visitorId(visitLog.getVisitorId())
                .residentId(visitLog.getResidentId())
                .guardId(visitLog.getGuardId())
                .entryTime(visitLog.getEntryTime())
                .exitTime(visitLog.getExitTime())
                .entryMethod(visitLog.getEntryMethod())
                .notes(visitLog.getNotes())
                .build();
    }
}