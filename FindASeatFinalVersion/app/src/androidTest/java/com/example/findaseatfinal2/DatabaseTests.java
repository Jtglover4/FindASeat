package com.example.findaseatfinal2;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DatabaseTests extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    private User testUser;

    public DatabaseTests() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

        // Initialize User object here to avoid any instrumentation issues.
        String affiliation = "Undergraduate";
        String email = "ramneek@usc.edu";
        String name = "Ramneek Singh";
        String password = "ramneek!";
        List<Integer> reservations = Arrays.asList(32); // Assuming this is the only reservation
        String studentID = "4219746589";

        // Create a new User object
        testUser = new User(studentID, name, email, password, affiliation, reservations);
    }

    // Add tearDown method if needed
    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finishOpenedActivities();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.tearDown();
    }
    @Test
    public void testUserAddedVerificationTest() {
        FirebaseVerifier fv = new FirebaseVerifier();
        IsPassed p = new IsPassed();
        p.setPassed(false);
        fv.verifyUserEnteredCorrectly("4219746589", testUser, p);
        try {
            Thread.sleep(2000);
            assertTrue(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.setPassed(false);
        fv.verifyUserEnteredCorrectly("0", testUser, p);
        try {
            Thread.sleep(2000);
            assertFalse(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.setPassed(false);
        testUser.setName("notramneek");
        fv.verifyUserEnteredCorrectly("4219746589", testUser, p);
        try {
            Thread.sleep(2000);
            assertFalse(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testResAddedVerificationTest() {
        FirebaseVerifier fv = new FirebaseVerifier();
        IsPassed p = new IsPassed();
        // Provided data
        int buildingID = 1;
        boolean cancelled = false;
        String date = "11/06/2023";
        boolean indoor = true;
        int numSlots = 1;
        int resID = 1;
        int startTime = 1130;

        // Create new Reservation object
        Reservation newReservation = new Reservation(resID, buildingID, indoor, date, startTime, numSlots, cancelled);
        p.setPassed(false);
        fv.verifyReservationEnteredCorrectly("1", newReservation, p);
        try {
            Thread.sleep(2000);
            assertTrue(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.setPassed(false);
        fv.verifyReservationEnteredCorrectly("-1", newReservation, p);
        try {
            Thread.sleep(2000);
            assertFalse(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.setPassed(false);
        newReservation.setIndoor(false);
        fv.verifyReservationEnteredCorrectly("1", newReservation, p);
        try {
            Thread.sleep(2000);
            assertFalse(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testResCancelledVerificationTest() {
        FirebaseVerifier fv = new FirebaseVerifier();
        IsPassed p = new IsPassed();

        p.setPassed(false);
        fv.verifyReservationCancelled("1", p, false);
        try {
            Thread.sleep(2000);
            assertTrue(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        p.setPassed(false);
        fv.verifyReservationCancelled("12", p, true);
        try {
            Thread.sleep(2000);
            assertTrue(p.isPassed());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}