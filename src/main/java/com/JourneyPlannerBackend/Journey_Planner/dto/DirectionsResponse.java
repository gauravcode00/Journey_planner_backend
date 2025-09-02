package com.JourneyPlannerBackend.Journey_Planner.dto;

public class DirectionsResponse {
    private String encodedPolyline;

    public DirectionsResponse(String encodedPolyline) {
        this.encodedPolyline = encodedPolyline;
    }

    // Getters and Setters
    public String getEncodedPolyline() { return encodedPolyline; }
    public void setEncodedPolyline(String encodedPolyline) { this.encodedPolyline = encodedPolyline; }
}
