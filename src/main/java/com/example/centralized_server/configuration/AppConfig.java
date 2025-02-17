package com.example.centralized_server.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {
    @Value("${api.key}")
    private String apiKey;

}
