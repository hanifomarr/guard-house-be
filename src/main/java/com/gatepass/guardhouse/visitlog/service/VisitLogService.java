package com.gatepass.guardhouse.visitlog.service;

import com.gatepass.guardhouse.common.exception.BusinessException;
import com.gatepass.guardhouse.common.exception.ErrorCode;
import com.gatepass.guardhouse.visitlog.dto.LogEntryRequest;
import com.gatepass.guardhouse.visitlog.dto.LogExitRequest;
import com.gatepass.guardhouse.visitlog.dto.VisitLogResponse;
import com.gatepass.guardhouse.visitlog.model.VisitLog;
import com.gatepass.guardhouse.visitlog.repository.VisitLogRepository;
import com.gatepass.guardhouse.visitor.dto.VisitorResponse;
import com.gatepass.guardhouse.visitor.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitorService visitorService;

    public VisitLogResponse logEntry(LogEntryRequest request, String guardId) {

        VisitorResponse visitor = visitorService.getVisitorById(request.getVisitorId());

        VisitLog visitLog = VisitLog.builder()
                .visitorId(request.getVisitorId())
                .residentId(visitor.getHostResidentId())
                .guardId(guardId)
                .entryTime(Instant.now())
                .entryMethod(request.getEntryMethod())
                .notes(request.getNotes())
                .build();

        VisitLog savedVisitLog = visitLogRepository.save(visitLog);
        return VisitLogResponse.from(savedVisitLog);
    }

    public VisitLogResponse logExit(LogExitRequest request, String visitLogId) {

        VisitLog visitLog = visitLogRepository.findById(visitLogId).orElseThrow(() -> new BusinessException(ErrorCode.VISIT_LOG_NOT_FOUND));
        if (visitLog.getExitTime() != null) {
            throw new BusinessException(ErrorCode.VISITOR_ALREADY_EXITED);
        }

        visitLog.setExitTime(Instant.now());
        visitLog.setNotes(request.getNotes());

        VisitLog savedVisitLog = visitLogRepository.save(visitLog);
        return VisitLogResponse.from(savedVisitLog);
    }

    public List<VisitLogResponse> getLogsByVisitor(String visitorId) {
        return visitLogRepository.findByVisitorId(visitorId)
                .stream()
                .map(VisitLogResponse::from)
                .toList();
    }

    public List<VisitLogResponse> getAllLogs() {
        return visitLogRepository.findAll()
                .stream()
                .map(VisitLogResponse::from)
                .toList();
    }
}