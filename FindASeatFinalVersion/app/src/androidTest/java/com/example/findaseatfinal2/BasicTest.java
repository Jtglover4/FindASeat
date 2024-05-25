package com.example.findaseatfinal2;


import static androidx.core.content.MimeTypeFilter.matches;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class BasicTest {

    @Rule
    public ActivityScenarioRule<Map> mActivityScenarioRule =
            new ActivityScenarioRule<>(Map.class);

    @Test
    public void basicTest() {
        // Assuming each marker has a unique title
        // You might need to adjust this based on your actual marker titles
        //Thread.sleep(10000);
        String[] markerTitles = {
                "",
                "Leavey Library",
                "Fertitta Hall",
                "Doheny Library",
                "Mudd Hall",
                "Taper Hall",
                "Annenberg Hall",
                "Norris Dental Center",
                "Seaver Science Center",
                "School of Architecture",
                "School of Cinema"
        };
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        for (String title : markerTitles) {
            //private UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
            UiObject mMarker1 = device.findObject(new UiSelector().descriptionContains(title));
            System.out.println(title + " : " + mMarker1.exists());
            if(!mMarker1.exists()){
                fail("Marker not found");
            }
            try {
                mMarker1.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
            UiObject closeButton = device.findObject(new UiSelector().resourceId("com.example.findaseatfinal2:id/reg_submit_tv"));
            try {
                closeButton.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void basicTestFail() {
        // Assuming each marker has a unique title
        // You might need to adjust this based on your actual marker titles
        //Thread.sleep(10000);
        String[] markerTitles = {
                "Grace Library"
        };

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        for (String title : markerTitles) {
            //private UiDevice uiDevice = UiDevice.getInstance(getInstrumentation());
            UiObject mMarker1 = device.findObject(new UiSelector().descriptionContains(title));
            System.out.println(title + " : " + mMarker1.exists());
            if(mMarker1.exists()){
                fail("Marker found which does not exist");
            }
            try {
                mMarker1.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
            UiObject closeButton = device.findObject(new UiSelector().resourceId("com.example.findaseatfinal2:id/reg_submit_tv"));
            try {
                closeButton.click();
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
