package it.petrillo.bonagatv.services;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class ResendEmailService {

    private String resendApiKey = "re_BFgkNnbg_Dxu8g1ejvZJni9Ki2iEbgzGg";

    private static final String RESEND_URL = "https://api.resend.com/emails";

    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + resendApiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> body = Map.of(
                    "from", "no-reply@bonagacommunication.tv",
                    "to", to,
                    "subject", subject,
                    "html", htmlContent
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(RESEND_URL, request, String.class);

            log.info("Email inviata a {}: {}", to, response.getStatusCode());

        } catch (Exception e) {
            log.error("Errore nell'invio dell'email a {}: {}", to, e.getMessage());
        }
    }
}
