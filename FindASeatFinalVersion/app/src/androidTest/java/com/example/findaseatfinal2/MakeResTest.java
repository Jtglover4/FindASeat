package com.example.findaseatfinal2;

import static org.junit.Assert.assertNotEquals;

import android.test.ActivityInstrumentationTestCase2;
import android.app.Activity;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;
public class MakeResTest  extends ActivityInstrumentationTestCase2<MainActivity>  {


    private Solo solo;

    public MakeResTest() {
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

        solo.sleep(3000);

        solo.clickOnScreen(312,882);

        solo.waitForText("Create Reservation");

        solo.clickOnText("Create Reservation");

        solo.clickOnScreen(780, 640);

        solo.clickOnText("9:00");
        solo.clickOnText("Make Reservation");

        solo.waitForText("Profile");
        solo.clickOnText("Profile");

        solo.sleep(3000);

        TextView textView = (TextView) solo.getView(R.id.textView14);
        // Assert that the TextView is not empty
        assertNotNull("TextView is null", textView);
        String textContent = textView.getText().toString();
        assertNotEquals("TextView is empty", "", textContent);

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}

