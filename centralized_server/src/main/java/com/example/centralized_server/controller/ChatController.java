package com.example.centralized_server.controller;

import com.example.centralized_server.dto.CheckBrandNameRequest;
import com.example.centralized_server.dto.SimpleChatRequest;
import com.example.centralized_server.dto.TrademarkAnalysis;
import com.example.centralized_server.service.DeepSeekChatService;
import feign.Body;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deepseek")
@AllArgsConstructor
public class ChatController {
    private final DeepSeekChatService deepSeekChatService;

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
}
