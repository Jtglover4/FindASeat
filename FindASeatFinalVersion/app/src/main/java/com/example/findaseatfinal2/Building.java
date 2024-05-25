package com.example.findaseatfinal2;

import java.util.List;

public class Building {

    private int buildingID;
    private String description;
    private String name;
    // Assume reservationIds is an array or list of strings representing reservation IDs.
    private List<Integer> reservationIds;

    public Building() {
        // Default constructor required for calls to DataSnapshot.getValue(Building.class)
    }

    public Building(int buildingID, String description, String name, List<Integer> reservationIds) {
        this.buildingID = buildingID;
        this.description = description;
        this.name = name;
        this.reservationIds = reservationIds;
    }

    // Getters and Setters
    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getReservationIds() {
        return reservationIds;
    }

    public void setReservationIds(List<Integer> reservationIds) {
        this.reservationIds = reservationIds;
    }

    // toString() method for printing
    @Override
    public String toString() {
        return "Building{" +
                "buildingID=" + buildingID +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", reservationIds=" + reservationIds +
                '}';
    }
}
