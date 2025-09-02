package com.JourneyPlannerBackend.Journey_Planner.dto;

public class DirectionsRequest {
    private String origin; // e.g., "current_location" or "lat,lng"
    private String destination;

    // Getters and Setters
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}
