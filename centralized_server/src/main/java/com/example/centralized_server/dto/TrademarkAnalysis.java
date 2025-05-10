package com.example.centralized_server.dto;

import java.util.List;

public class TrademarkAnalysis {

    private List<String> potentiallySimilarBrands;
    private String riskAssessment; // Ví dụ: "Cao", "Trung bình", "Thấp", "Rất thấp"
    private String explanation;

    // Constructors
    public TrademarkAnalysis() {
    }

    public TrademarkAnalysis(List<String> potentiallySimilarBrands, String riskAssessment, String explanation) {
        this.potentiallySimilarBrands = potentiallySimilarBrands;
        this.riskAssessment = riskAssessment;
        this.explanation = explanation;
    }

    // Getters and Setters
    public List<String> getPotentiallySimilarBrands() {
        return potentiallySimilarBrands;
    }

    public void setPotentiallySimilarBrands(List<String> potentiallySimilarBrands) {
        this.potentiallySimilarBrands = potentiallySimilarBrands;
    }

    public String getRiskAssessment() {
        return riskAssessment;
    }

    public void setRiskAssessment(String riskAssessment) {
        this.riskAssessment = riskAssessment;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "TrademarkAnalysis{" +
                "potentiallySimilarBrands=" + potentiallySimilarBrands +
                ", riskAssessment='" + riskAssessment + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}