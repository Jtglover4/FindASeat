package com.example.findaseatfinal2;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;
public class UserExistsLoginTest  extends ActivityInstrumentationTestCase2<MainActivity>  {


    private Solo solo;

    public UserExistsLoginTest() {
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
        solo.enterText(0, "user10@usc.edu");

        // Enter password
        solo.clearEditText(1); // Assuming this is the second EditText
        solo.enterText(1, "zxcvbnm*");

        // Hide the keyboard
        solo.hideSoftKeyboard();

        // Click on the 'Submit' button
        solo.clickOnButton("Submit");

        // Wait for error message
        boolean errorMessageFound = solo.waitForText("Profile", 1, 5000);
        assertTrue("Login Worked", errorMessageFound);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
