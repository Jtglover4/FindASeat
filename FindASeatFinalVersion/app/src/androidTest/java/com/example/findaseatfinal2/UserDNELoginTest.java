package com.example.findaseatfinal2;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

public class UserDNELoginTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public UserDNELoginTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testUserDNELogin() throws Exception {
        // Click on the 'Profile' tab
        solo.clickOnText("Profile");

        // Enter email
        solo.clearEditText(0); // Assuming this is the first EditText
        solo.enterText(0, "hello@usc.edu");

        // Enter password
        solo.clearEditText(1); // Assuming this is the second EditText
        solo.enterText(1, "ghghghgh&");

        // Hide the keyboard
        solo.hideSoftKeyboard();

        // Click on the 'Submit' button
        solo.clickOnButton("Submit");

        // Wait for error message
        boolean errorMessageFound = solo.waitForText("Cannot find user", 1, 5000);
        assertTrue("Error message not displayed", errorMessageFound);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
