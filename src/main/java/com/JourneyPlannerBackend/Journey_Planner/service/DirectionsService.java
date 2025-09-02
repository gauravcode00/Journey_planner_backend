package com.JourneyPlannerBackend.Journey_Planner.service;

import com.JourneyPlannerBackend.Journey_Planner.dto.DirectionsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class DirectionsService {

    @Value("${GOOGLE_MAPS_API_KEY}") // Use a separate key for Maps if needed
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getDirections(DirectionsRequest request) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&key=%s",
                request.getOrigin(),
                request.getDestination(),
                apiKey
        );

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");
            if (routes != null && !routes.isEmpty()) {
                Map<String, Object> overviewPolyline = (Map<String, Object>) routes.get(0).get("overview_polyline");
                return (String) overviewPolyline.get("points");
            }
            return ""; // Return empty if no route is found
        } catch (Exception e) {
            System.err.println("Error calling Directions API: " + e.getMessage());
            return "";
        }
    }
}
