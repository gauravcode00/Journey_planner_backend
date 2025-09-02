package com.JourneyPlannerBackend.Journey_Planner.service;
import com.JourneyPlannerBackend.Journey_Planner.dto.JourneyRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class GeminiService {

    // Inject the API Key from application.properties
    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateItinerary(JourneyRequest request) {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = buildPrompt(request);

        Map<String, Object> part = new HashMap<>();
        part.put("text", prompt);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", Collections.singletonList(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Collections.singletonList(content));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            // Navigate through the complex JSON response from Gemini
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.getBody().get("candidates");
            Map<String, Object> responseContent = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) responseContent.get("parts");
            String itineraryText = (String) parts.get(0).get("text");

            // Clean the response to ensure it's valid JSON
            return itineraryText.replace("```json", "").replace("```", "").trim();

        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            // Return an error JSON so the app doesn't crash
            return "{\"error\":\"Failed to generate itinerary from AI. Please try again.\"}";
        }
    }

    private String buildPrompt(JourneyRequest request) {
        // This is the "magic" part. We instruct the AI on exactly what we want.
        return String.format(
                "You are an expert travel guide for India. A user wants to plan a trip. " +
                        "Generate a detailed itinerary based on these details:\n" +
                        "- Cities/Region: %s\n" +
                        "- Duration: %d days\n" +
                        "- Main Interests: %s\n\n" +
                        "Provide a day-by-day plan. For each day, suggest 2-3 activities or places with a short, engaging one-sentence description. " +
                        "IMPORTANT: Your entire response MUST be a single, clean JSON object. Do not include any text before or after the JSON. " +
                        "The JSON object should have a single key 'itinerary' which is an array of day objects. " +
                        "Each day object must have a 'day' (e.g., 'Day 1'), a 'title' (e.g., 'Arrival and Spiritual Immersion'), and a 'plan' which is an array of activity objects. " +
                        "Each activity object must have 'time' (e.g., 'Morning', 'Afternoon'), 'place_name', and 'description'.",
                request.getDestinations(),
                request.getDurationInDays(),
                request.getInterests().toString()
        );
    }
}
