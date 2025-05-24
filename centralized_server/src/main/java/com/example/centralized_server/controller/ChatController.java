package com.example.centralized_server.controller;

import com.example.centralized_server.dto.CheckBrandNameRequest;
import com.example.centralized_server.dto.LogoPromptRequest;
import com.example.centralized_server.dto.SimpleChatRequest;
import com.example.centralized_server.dto.TrademarkAnalysis;
import com.example.centralized_server.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deepseek")
@AllArgsConstructor
public class ChatController {
    private final OpenAiService deepSeekChatService;

    @PostMapping("/simple-chat")
    public ResponseEntity<String> simpleChat(@RequestBody SimpleChatRequest request) {
        System.out.println(request.toString());
        String response = deepSeekChatService.getSimpleChatResponse(request.getMessage());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/check")
    public ResponseEntity<TrademarkAnalysis> checkTrademark(@RequestBody CheckBrandNameRequest request) {
        TrademarkAnalysis result = deepSeekChatService.checkTrademark(request.getBrandName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logo")
    public ResponseEntity<Image> generateLogo(@RequestBody LogoPromptRequest request) {
        var result = deepSeekChatService.generateLogo(request);
        return ResponseEntity.ok(result);
    }
}
