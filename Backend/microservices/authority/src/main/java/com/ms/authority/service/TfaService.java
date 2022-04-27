package com.ms.authority.service;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrDataFactory;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

import static com.ms.authority.utils.Authorities.Messages.INVALID_TFA_CODE_MSG;
import static com.ms.authority.utils.Authorities.PROJECT_NAME;

@Service
@AllArgsConstructor
public class TfaService {

    private final SecretGenerator secretGenerator;
    private final QrDataFactory qrDataFactory;
    private final QrGenerator qrGenerator;
    private final CodeVerifier verifier;

    public String generateSecretKey() {
        return secretGenerator.generate();
    }

    public String getQRCode(String label, String secret) throws QrGenerationException {
        QrData data = qrDataFactory
                .newBuilder()
                .label(label)
                .secret(secret)
                .issuer(PROJECT_NAME)
                .build();

        return Utils.getDataUriForImage(qrGenerator.generate(data), qrGenerator.getImageMimeType());
    }

    public boolean verifyCode(String secret, String code) {
        validateCode(code);
        return verifier.isValidCode(secret, code);
    }

    private void validateCode(String code) {
        try {
            Integer.parseInt(code);
        } catch (NumberFormatException nfe) {
            throw new InvalidGrantException(INVALID_TFA_CODE_MSG);
        }
    }
}
