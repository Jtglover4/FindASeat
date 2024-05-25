package com.example.findaseatfinal2;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import java.time.LocalDateTime;
import java.time.Month;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@Config(sdk = 28)
// Specify one or more SDK version numbers
public class ExampleTest3{
    @Test
    public void emailValidate() {

        RegistrationActivity RA = new RegistrationActivity();

        assertEquals(true, RA.isEmailValid("julian@usc.edu"));
        assertEquals(false, RA.isEmailValid(""));
        assertEquals(false, RA.isEmailValid("julian@usc,ed"));
    }
    @Test
    public void passwordValidate() {

        RegistrationActivity RA = new RegistrationActivity();

        assertEquals(true, RA.isPasswordValid("djhdhhdshj&"));
        assertEquals(false, RA.isPasswordValid("hdshjhdhdhhd"));
        assertEquals(false, RA.isPasswordValid("hdhh&"));
        assertEquals(false, RA.isPasswordValid(""));
    }
    @Test
    public void IDValidate() {

        RegistrationActivity RA = new RegistrationActivity();

        assertEquals(true, RA.isIDValid("6745674556"));
        assertEquals(false, RA.isIDValid(""));
        assertEquals(false, RA.isIDValid("1727"));
        assertEquals(false, RA.isIDValid("3783273787327837"));
    }
    @Test
    public void nameValidate() {

        RegistrationActivity RA = new RegistrationActivity();

        assertEquals(true, RA.isNameValid("Julian"));
        assertEquals(false, RA.isNameValid(""));
    }
    @Test
    public void affiliationValidate() {

        RegistrationActivity RA = new RegistrationActivity();

        assertEquals(true, RA.isAffiliationSelected("Graduate"));
        assertEquals(false, RA.isAffiliationSelected("Select:"));

    }
    @Test
    public void loginEmailValidate(){

        LoginActivity LA = new LoginActivity();

        assertEquals(true, LA.isEmailValid("julian@usc.edu"));
        assertEquals(false, LA.isEmailValid(""));
        assertEquals(false, LA.isEmailValid("j@usc.ed"));

    }
    @Test
    public void loginPasswordValidate(){

        LoginActivity LA = new LoginActivity();

        assertEquals(true, LA.isPasswordValid("djhdhhdshj&"));
        assertEquals(false, LA.isPasswordValid("hdshjdhhdhhd"));
        assertEquals(false, LA.isPasswordValid("hdhh&"));
        assertEquals(false, LA.isPasswordValid(""));

    }

//    @Test
//    public void validateReservation(){
//
//        ReservationActivity RA = new ReservationActivity();
//
//        View squareItem = LayoutInflater.from(this).inflate(R.layout.square_item, null);
//        View square = squareItem.findViewById(R.id.box);
//
//        assertEquals(RA.validChecker(square, 3), 1);
//
//    }

    @Test
    public void resetSquaresTest(){

        ReservationActivity RA = new ReservationActivity();

        List<View> views = new ArrayList<>();

        for(int i = 0; i < 48; i++){
            //textView.setText("TextView " + (i + 1)); // Set text as needed
            View v = new View(RuntimeEnvironment.application);
            v.setTag(2);
            views.add(v);
        }
        views.get(30).setTag(0);
        views.get(35).setTag(1);
        RA.resetSquares(views);

        Drawable backgroundDrawable = views.get(20).getBackground();
        int backgroundColor = ((ColorDrawable) backgroundDrawable).getColor();

        Drawable backgroundDrawable2 = views.get(30).getBackground();
        int backgroundColor2 = ((ColorDrawable) backgroundDrawable2).getColor();

        Drawable backgroundDrawable3 = views.get(35).getBackground();
        int backgroundColor3 = ((ColorDrawable) backgroundDrawable3).getColor();

        assertEquals(backgroundColor, Color.GREEN);
        assertEquals(backgroundColor2, Color.RED);
        assertEquals(backgroundColor3, Color.GREEN);
    }

    @Test
    public void tagTest(){

        ReservationActivity RA = new ReservationActivity();

        List<View> views = new ArrayList<>();

        for(int i = 0; i < 48; i++){
            //textView.setText("TextView " + (i + 1)); // Set text as needed
            View v = new View(RuntimeEnvironment.application);
            v.setTag(2);
            v.setId(i);
            views.add(v);
        }
        for(int i = 30; i < 32; i++){
            views.get(i).setTag(0);
        }
        for(int i = 40; i < 48; i++){
            views.get(i).setTag(-1);
        }


        View s = views.get(20);
        View t = views.get(29);
        View x = views.get(42);
        assertEquals(true, RA.validChecker(views, s, 3));
        assertEquals(false, RA.validChecker(views, t, 4));
        assertEquals(false, RA.validChecker(views, x, 2));

    }
    @Test
    public void timeHasPassedTest(){

        LocalDateTime dateTime = LocalDateTime.of(2023, Month.NOVEMBER, 11, 15, 0);

        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        //String h = "1900";
        int index = 40;
        String resdate1 = "12/01/2023";

        ProfileActivity PF = new ProfileActivity();

        assertEquals(true, PF.validateTime(date, index, resdate1));
        assertEquals(false, PF.validateTime(date, 40, "10/30/2023"));
        assertEquals(false, PF.validateTime(date, 40, "11/01/2023"));
        assertEquals(false, PF.validateTime(date, 28, "11/11/2023"));
        assertEquals(true, PF.validateTime(date, 34, "11/11/2023"));
    }
    @Test
    public void validResTime(){

        LocalDateTime dateTime = LocalDateTime.of(2023, Month.NOVEMBER, 20, 15, 0);

        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        ReservationActivity RA = new ReservationActivity();

        assertEquals(true, RA.validTime(date, 32, 0));
        assertEquals(true, RA.validTime(date, 28, 1));
        assertEquals(false, RA.validTime(date, 28, 0));


    }
    @Test
    public void CheckBuildingSpotsAvail(){
        List<View> views = new ArrayList<>();
        List<TextView> tview = new ArrayList<>();
        ReservationActivity RA = new ReservationActivity();

        for(int i=0; i<48; i++){
            tview.add(new TextView(RuntimeEnvironment.application));
            views.add(new View(RuntimeEnvironment.application));
        }

        RA.setTagsForViews(views, tview);

        assertEquals(-1,views.get(0).getTag());
        assertEquals(2, views.get(20).getTag());
        assertEquals(2, views.get(39).getTag());
        assertEquals(2, views.get(17).getTag());
        assertEquals(-1, views.get(16).getTag());
        assertEquals(-1, views.get(40).getTag());
        assertEquals(-1, views.get(47).getTag());
    }
    @Test
    public void CheckAllBuildingSlotsAvail(){
        List<View> views = new ArrayList<>();
        List<TextView> tview = new ArrayList<>();
        ReservationActivity RA = new ReservationActivity();

        for(int i=0; i<48; i++){
            tview.add(new TextView(RuntimeEnvironment.application));
            views.add(new View(RuntimeEnvironment.application));
        }

        RA.setTagsForViews(views, tview);

        for(int i=0; i<48; i++){
            if(i>16 && i<40){
                assertEquals(2, views.get(i).getTag());
            }
            else{
                assertEquals(-1, views.get(i).getTag());
            }
        }
    }

}
