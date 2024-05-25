package com.example.findaseatfinal2;

import java.util.HashMap;
import java.util.Objects;

public class Reservation {

    private int resID;
    private int buildingID;
    private boolean indoor;
    private String date;
    private int startTime;
    private int numSlots;

    private boolean cancelled = false;

    public Reservation(){

    }


    public Reservation(int resID, int buildingID, boolean indoor, String date, int startTime, int numSlots, boolean cancelled) {
        this.resID = resID;
        this.buildingID = buildingID;
        this.indoor = indoor;
        this.date = date;
        this.startTime = startTime;
        this.numSlots = numSlots;
        this.cancelled = cancelled;
    }
    public Reservation(Reservation other) {
        this.resID = other.resID;
        this.buildingID = other.buildingID;
        this.indoor = other.indoor;
        this.date = other.date;
        this.startTime = other.startTime;
        this.numSlots = other.numSlots;
        this.cancelled = other.cancelled;
    }

    public boolean equalsTo(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return resID == that.resID &&
                buildingID == that.buildingID &&
                indoor == that.indoor &&
                startTime == that.startTime &&
                numSlots == that.numSlots &&
                cancelled == that.cancelled &&
                Objects.equals(date, that.date);
    }

    public String toString(){
        HashMap<Integer, String> reversedBuildingMap = new HashMap<>();
        reversedBuildingMap.put(1, "Leavey Library");
        reversedBuildingMap.put(2, "Fertitta Hall");
        reversedBuildingMap.put(3, "Doheny Library");
        reversedBuildingMap.put(4, "Mudd Hall");
        reversedBuildingMap.put(5, "Taper Hall");
        reversedBuildingMap.put(6, "Annenberg Hall");
        reversedBuildingMap.put(7, "Norris Dental Center");
        reversedBuildingMap.put(8, "Seaver Science Center");
        reversedBuildingMap.put(9, "School of Architecture");
        reversedBuildingMap.put(10, "School of Cinema");

        return reversedBuildingMap.get(buildingID) + " | " + date +  " | " + startTime;
    }


    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getNumSlots() {
        return numSlots;
    }

    public void setNumSlots(int numSlots) {
        this.numSlots = numSlots;
    }

    public boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
