package com.example.findaseatfinal2;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class XMLActivityPair {
    private String xmlName;
    private Class<?> activity_class;

    public XMLActivityPair(String xmlName, Class<?> activity_class) {
        this.xmlName = xmlName;
        this.activity_class = activity_class;
    }

    public void defaultSwitchToPage(AppCompatActivity parent){
        if(activity_class != null)
        {
            System.out.println("SETTING NEW ACTIVTY");
            changeToActivity(parent);
        } else if(!xmlName.equals(""))
        {
            System.out.println("SHOWING NEW XML");
            setViewToXML(parent);
        } else {
            System.out.println("ERROR: BROKEN PAIR");
        }
    }

    public void setViewToXML(AppCompatActivity parent){
        int layoutId = parent.getResources().getIdentifier(xmlName, "layout", parent.getPackageName());
        if (layoutId != 0) {
            // The layout ID was found
            parent.setContentView(layoutId); // Set the content view to the layout
        } else {
            System.out.println("layout not found");
        }
    }

    public void changeToActivity(AppCompatActivity parent){
        Intent intent = new Intent(parent, activity_class);
        parent.startActivity(intent);
    }
}
