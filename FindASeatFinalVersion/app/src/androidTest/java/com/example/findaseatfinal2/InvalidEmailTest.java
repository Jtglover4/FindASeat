package com.example.findaseatfinal2;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

public class InvalidEmailTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public InvalidEmailTest() {
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

        solo.clickOnText("Sign Up");

        // Enter email
        solo.clearEditText(0); // Assuming this is the first EditText
        solo.enterText(0, "user7@uscedu");

        // Enter password
        solo.clearEditText(1); // Assuming this is the second EditText
        solo.enterText(1, "ghghghgh&");

        solo.clearEditText(2);
        solo.enterText(2, "Tester");

        solo.clearEditText(3);
        solo.enterText(3, "2345678901");

        solo.clickOnScreen(750, 1020);
        solo.clickOnText("Graduate");

        solo.clickOnText("Upload Image");

        solo.sleep(6000);

        solo.clickOnText("Submit");
        // Hide the keyboard

        boolean errorMessageFound = solo.waitForText("Invalid Email", 1, 5000);
        assertTrue("Fail worked", errorMessageFound);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
