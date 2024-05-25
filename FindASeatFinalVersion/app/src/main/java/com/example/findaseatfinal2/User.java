package com.example.findaseatfinal2;

import java.util.List;
import java.util.Objects;

public class User {

    public String studentID;

    public String name;
    private String email;
    private String password;
    private String affiliation;
    private List<Integer> reservations;

    public User(){

    }

    public User(String studentID, String name, String email, String password, String affiliation, List<Integer> reservations) {
        this.studentID = studentID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.reservations = reservations;
    }
    public boolean equalsTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(studentID, user.studentID) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(affiliation, user.affiliation) &&
                Objects.equals(reservations, user.reservations);
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public List<Integer> getReservations() {
        return reservations;
    }

    public void addReservationID(Integer reservation) {
        reservations.add(reservation);
    }

    public void removeReservationID(Integer reservation) {
        reservations.remove(reservation);
    }

    public void setReservations(List<Integer> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "User{" +
                "studentID=" + studentID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
