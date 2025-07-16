package com.example.Unit_2_Project.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;
    private static final int MAX_TOKENS = 500;  // roughly how long the reply can be


    public GeminiService() {
        this.client = Client.builder()
                .apiKey(System.getenv("GOOGLE_API_KEY"))
                .build();
    }

    /**
     * Ask Gemini to explain a quiz question, optionally with context.
     */
    public String explainQuestion(String question, String context) {
        StringBuilder prompt = new StringBuilder()
                .append("You are an expert tutor. Explain this quiz question:\n\n")
                .append("Question: ").append(question).append("\n");
        if (context != null && !context.isBlank()) {
            prompt.append("Context: ").append(context).append("\n");
        }

        // limit tokens so the AI won’t return pages of text
        GenerateContentResponse resp = client.models.generateContent(
                "gemini-2.0-flash-001",
                prompt.toString(),
                GenerateContentRequest.Params.newBuilder()
                        .setMaxOutputTokens(MAX_TOKENS)
                        .build()
        );
        return resp.text();
    }
}

