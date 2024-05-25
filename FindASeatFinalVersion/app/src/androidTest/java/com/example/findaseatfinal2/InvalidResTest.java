package com.example.findaseatfinal2;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

public class InvalidResTest extends ActivityInstrumentationTestCase2<MainActivity> {


    private Solo solo;

    public InvalidResTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void tester() throws Exception {
        // Click on the 'Profile' tab
        solo.clickOnText("Profile");

        // Enter email
        solo.clearEditText(0); // Assuming this is the first EditText
        solo.enterText(0, "user5@usc.edu");

        // Enter password
        solo.clearEditText(1); // Assuming this is the second EditText
        solo.enterText(1, "ghghghgh&");

        // Hide the keyboard
        solo.hideSoftKeyboard();

        // Click on the 'Submit' button
        solo.clickOnButton("Submit");

        solo.sleep(3000);

        solo.clickOnScreen(312,882);

        solo.waitForText("Create Reservation");

        solo.clickOnText("Create Reservation");

        solo.clickOnText("Make Reservation");

        boolean errorMessageFound = solo.waitForText("Please select the number of slots and your start time", 1, 5000);
        assertTrue("Invalid Res Pass", errorMessageFound);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
