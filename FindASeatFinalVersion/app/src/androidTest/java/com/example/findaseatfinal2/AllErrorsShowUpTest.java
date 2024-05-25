package com.example.findaseatfinal2;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

public class AllErrorsShowUpTest extends ActivityInstrumentationTestCase2<MainActivity> {


    private Solo solo;

    public AllErrorsShowUpTest() {
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

        solo.clickOnText("Submit");
        // Hide the keyboard


        boolean errorEmailFound = solo.waitForText("Invalid Email", 1, 5000);
        boolean errorPasswordFound = solo.waitForText("Invalid Password Input", 1, 5000);
        boolean errorName = solo.waitForText("Invalid Input", 1, 5000);
        boolean errorID = solo.waitForText("Invalid ID", 1, 5000);
        boolean errorAff = solo.waitForText("Please Select An Affiliation", 1, 5000);
        boolean errorPhoto = solo.waitForText("Please Upload an Image");

        assertTrue("Fail email worked", errorEmailFound);
        assertTrue("Fail password worked", errorPasswordFound);
        assertTrue("Fail name worked", errorName);
        assertTrue("Fail ID", errorID);
        assertTrue("Fail affiiliation", errorAff);
        assertTrue("Fail photo", errorPhoto);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
