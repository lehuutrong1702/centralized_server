package com.example.centralized_server.service;

import com.example.centralized_server.dto.LogoPromptRequest;
import com.example.centralized_server.dto.TrademarkAnalysis;
import com.example.centralized_server.utils.LogoPromptGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAiService {
    @Autowired
    private OpenAiChatModel chatModel;

    @Autowired
    private OpenAiImageModel imageModel;

    public String getSimpleChatResponse(String message) {
        System.out.println(message);
        try {
            Prompt prompt = new Prompt(message,
                    OpenAiChatOptions.builder()
                            .model("gpt-4o")
                            .temperature(0.4)
                            .build());
            ChatResponse response = chatModel.call(
                    prompt);
            return response.getResult().getOutput().getText();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public TrademarkAnalysis checkTrademark(String brandNameToCheck) {
        String systemPromptForTrademark = """
                You are an AI assistant specializing in preliminary brand name analysis.
                Your task is to receive an input brand name and, based on your general knowledge,
                search for existing brand names that appear similar or could be easily confused.
                Then, provide your assessment as a JSON object.
                
                The JSON object must have the following fields:
                1. "potentiallySimilarBrands": An array of strings listing brand names you found that are potentially similar. If none are found, return an empty array [].
                2. "riskAssessment": A string indicating the potential risk of conflict. CHOOSE ONLY ONE OF THE FOLLOWING VALUES: "High", "Medium", "Low", or "Very Low".
                3. "explanation": A string providing a brief explanation for your risk assessment and listing a few reasons if applicable. If no clear similarities are found, include a disclaimer that this is not a legal search.
                
                Example of the desired JSON format when similarities are found:
                {
                  "potentiallySimilarBrands": ["ExampleBrand Global", "Example Brand Inc.", "XampleBrand"],
                  "riskAssessment": "Medium",
                  "explanation": "The brand name contains 'ExampleBrand', which is a relatively common term, and several variations are already in use. Further legal due diligence is strongly recommended."
                }
                
                Example of the desired JSON format when few or no clear similarities are found:
                {
                  "potentiallySimilarBrands": [],
                  "riskAssessment": "Very Low",
                  "explanation": "Based on general knowledge, no clearly similar brand names to 'brandNameToCheck' were found. However, this is NOT an official legal trademark search, and you should conduct a professional search for legally accurate results."
                }
                
                IMPORTANT NOTE: This analysis is based on general knowledge only and is NOT an official legal trademark search. The user must conduct a professional search for legally accurate results.
                Please ONLY respond with a valid JSON object, without any other explanatory text outside of the JSON object itself.
                """;

        String userQuery = "Please analyze the following brand name: \"" + brandNameToCheck + "\"";


        try {
            Prompt prompt = new Prompt(
                    String.valueOf(List.of(
                            new Prompt(systemPromptForTrademark),
                            new Prompt(userQuery)
                    )),
                    OpenAiChatOptions.builder()
                            .model("gpt-4o")
                            .temperature(0.3)
                            .responseFormat(ResponseFormat.builder().type(ResponseFormat.Type.JSON_OBJECT).build())
                            .build()
            );

            ChatResponse response = chatModel.call(prompt);
            var map = response.getResult().getOutput().getText();
            System.out.println(map);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue( map, TrademarkAnalysis.class);

        } catch (Exception e) {
            System.err.println("Trademark check failed: " + e.getMessage());
            return new TrademarkAnalysis(
                    List.of(),
                    "Error",
                    "Failed to parse AI response or call API. Details: " + e.getMessage()
            );
        }
    }

    public Image generateLogo(LogoPromptRequest request) {
        var response = imageModel.call(
                new ImagePrompt(LogoPromptGenerator.generatePrompt(request),
                        OpenAiImageOptions.builder()
                                .quality("hd")
                                .N(1)
                                .height(1024)
                                .width(1024).build())
        );
        return response.getResult().getOutput();
    }

}
