package com.example.centralized_server.service;


import com.example.centralized_server.configuration.AppConfig;
import com.example.centralized_server.dto.EmailRequest;
import com.example.centralized_server.dto.EmailResponse;
import com.example.centralized_server.dto.SendEmailRequest;
import com.example.centralized_server.dto.Sender;
import com.example.centralized_server.repository.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

    private final EmailClient emailClient;
    AppConfig appConfig;




    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("HCMUS-INS")
                        .email("nguyenpv.30@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(appConfig.getApiKey(), emailRequest);
        } catch (FeignException e) {
            throw new RuntimeException("Can not send email");
        }
    }
}