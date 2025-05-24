package com.example.centralized_server.utils;

import com.example.centralized_server.dto.LogoPromptRequest;

public class LogoPromptGenerator {
    public static String generatePrompt(LogoPromptRequest input) {
        return String.format(
                "Design a clean and modern logo featuring ONLY a single, standalone logo icon and the company name \"%s\". " +
                        "The logo should be simple, professional, and easily recognizable without any background scenes or extra elements. " +
                        "Use %s as the primary color and %s as accent colors to create a sleek and polished look. " +
                        "Include visual elements such as %s, and ensure the company name is clearly integrated into the logo design. " +
                        "The design must be vector-style, branding-ready, and placed on a plain white background.",
                input.getCompanyName(),
                input.getPrimaryColor(),
                input.getAccentColors(),
                input.getSuggestedSymbols()
        );

    }
}