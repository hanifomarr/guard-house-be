package com.gatepass.guardhouse.common.util;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class QrCodeUtil {

    public String generateQrData(String qrToken, Instant expiry) {
        return String.join(":", "VISITOR", qrToken, expiry.toString());
    }

    public byte[] generateQrCode(String data) {
        return new byte[0];
    }


}
