package com.JourneyPlannerBackend.Journey_Planner.controller;

import com.JourneyPlannerBackend.Journey_Planner.dto.DirectionsRequest;
import com.JourneyPlannerBackend.Journey_Planner.dto.DirectionsResponse;
import com.JourneyPlannerBackend.Journey_Planner.dto.JourneyRequest;
import com.JourneyPlannerBackend.Journey_Planner.dto.JourneyResponse;
import com.JourneyPlannerBackend.Journey_Planner.service.DirectionsService;
import com.JourneyPlannerBackend.Journey_Planner.service.GeminiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/journey")
public class PlannerController {

    @Autowired
    private GeminiService geminiService;

    // NEW: Inject the DirectionsService
    @Autowired
    private DirectionsService directionsService;

    @PostMapping("/plan")
    public JourneyResponse createJourneyPlan(@RequestBody JourneyRequest request) {
        System.out.println("Received request. Forwarding to Gemini API...");
        String itineraryJson = geminiService.generateItinerary(request);
        System.out.println("Received response from Gemini. Sending to Flutter app.");
        return new JourneyResponse(itineraryJson);
    }

    // NEW: Add a new endpoint for getting directions
    @PostMapping("/directions")
    public DirectionsResponse getDirections(@RequestBody DirectionsRequest request) {
        System.out.println("Received directions request for destination: " + request.getDestination());
        String polyline = directionsService.getDirections(request);
        return new DirectionsResponse(polyline);
    }
}
