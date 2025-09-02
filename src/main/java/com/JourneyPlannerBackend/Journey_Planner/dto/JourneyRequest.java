package com.JourneyPlannerBackend.Journey_Planner.dto;

import java.util.Set;

public class JourneyRequest {
    private String destinations;
    private int durationInDays;
    private Set<String> interests;
    // Add other fields like budget if needed

    // Getters and Setters
    public String getDestinations() {
        return destinations;
    }

    public void setDestinations(String destinations) {
        this.destinations = destinations;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }
}
