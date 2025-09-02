package com.JourneyPlannerBackend.Journey_Planner.dto;

public class JourneyResponse {
    private String message;
    private Object itinerary; // We'll define a proper Itinerary model later

    public JourneyResponse(String message) {
        this.message = message;
    }

    public JourneyResponse(String message, Object itinerary) {
        this.message = message;
        this.itinerary = itinerary;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getItinerary() {
        return itinerary;
    }

    public void setItinerary(Object itinerary) {
        this.itinerary = itinerary;
    }
}