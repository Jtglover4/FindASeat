package com.example.findaseatfinal2;

import static org.junit.Assert.assertNotEquals;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

public class ModifyTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public ModifyTest() {
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

        solo.waitForText("Profile");
        solo.clickOnText("Profile");

        solo.waitForText("Modify");
        solo.clickOnText("Modify");

        solo.waitForText("Yes");
        solo.clickOnText("Yes");

        solo.sleep(2000);

        solo.clickOnScreen(250, 800);
        solo.clickOnScreen(250, 1000);

        solo.clickOnScreen(780, 640);

        solo.clickOnText("13:30");

        solo.clickOnText("Make Reservation");

        solo.waitForText("Profile");
        solo.clickOnText("Profile");

        solo.sleep(2000);

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

