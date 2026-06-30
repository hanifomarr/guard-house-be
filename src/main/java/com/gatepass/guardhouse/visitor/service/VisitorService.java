package com.gatepass.guardhouse.visitor.service;

import com.gatepass.guardhouse.common.exception.BusinessException;
import com.gatepass.guardhouse.common.exception.ErrorCode;
import com.gatepass.guardhouse.common.util.QrCodeUtil;
import com.gatepass.guardhouse.visitor.dto.RegisterVisitorRequest;
import com.gatepass.guardhouse.visitor.dto.VisitorResponse;
import com.gatepass.guardhouse.visitor.model.Visitor;
import com.gatepass.guardhouse.visitor.model.VisitorStatus;
import com.gatepass.guardhouse.visitor.repository.VisitorRepository;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final QrCodeUtil qrCodeUtil;

    public VisitorResponse registerVisitor(RegisterVisitorRequest request, String registeredBy) {

        String qrToken = UUID.randomUUID().toString();
        Instant expiry = switch (request.getType()) {
            case ONE_TIME -> Instant.now().plus(24, ChronoUnit.HOURS);
            case RECURRING -> Instant.now().plus(7, ChronoUnit.DAYS);
            case PARENT -> Instant.now().plus(30, ChronoUnit.DAYS);
        };
        String qrData = qrCodeUtil.generateQrData(qrToken, expiry);

        Visitor visitor = Visitor.builder()
                .name(request.getName())
                .icOrPassport(request.getIcOrPassport())
                .phone(request.getPhone())
                .type(request.getType())
                .registeredBy(registeredBy)
                .hostResidentId(request.getHostResidentId())
                .purpose(request.getPurpose())
                .expectedDate(request.getExpectedDate())
                .status(VisitorStatus.PENDING)
                .qrToken(qrToken)
                .qrCode(qrData)
                .qrExpiry(expiry)
                .build();

        Visitor savedVisitor = visitorRepository.save(visitor);
        return VisitorResponse.from(savedVisitor);
    }

    public VisitorResponse getVisitorById(String id) {

        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.VISITOR_NOT_FOUND));
        return VisitorResponse.from(visitor);
    }

    public List<VisitorResponse> getAllVisitors() {
        return visitorRepository.findAll()
                .stream()
                .map(VisitorResponse::from)
                .toList();
    }

    public List<VisitorResponse> getVisitorsByHostResidentId(String hostResidentId) {

        return visitorRepository
                .findAllByHostResidentId(hostResidentId)
                .stream()
                .map(VisitorResponse::from)
                .toList();
    }

    public VisitorResponse updateVisitorStatus(String id, VisitorStatus status) {

        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.VISITOR_NOT_FOUND));
        visitor.setStatus(status);

        Visitor savedVisitor = visitorRepository.save(visitor);
        return VisitorResponse.from(savedVisitor);
    }

    public byte[] getVisitorQrCode(String id) {
        Visitor visitor = visitorRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.VISITOR_NOT_FOUND));

        try {
            return qrCodeUtil.generateQrCode(visitor.getQrCode());
        } catch (WriterException | IOException e) {
            throw new BusinessException(ErrorCode.VISITOR_QR_FAILED);
        }
    }
}
