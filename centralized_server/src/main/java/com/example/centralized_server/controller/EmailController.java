package com.example.centralized_server.controller;


import com.example.centralized_server.dto.EmailResponse;
import com.example.centralized_server.dto.SendEmailRequest;
import com.example.centralized_server.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/notification/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    public ResponseEntity<EmailResponse> sendEmail(@RequestBody SendEmailRequest request){
        System.out.println(request.getTo().getEmail());
        EmailResponse emailResponse = emailService.sendEmail(request);
        return ResponseEntity.ok(emailResponse);
    }
}
