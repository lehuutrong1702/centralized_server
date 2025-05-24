package com.example.centralized_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoPromptRequest {
    private String logoType;
    private String companyName;
    private String primaryColor;
    private String accentColors;
    private String suggestedSymbols;
}
