package com.example.findaseatfinal2;

import java.util.HashMap;

public class LoggedIn {




    static public boolean isUserLoggedIn;

    static public String userID;

    public static LoggedIn instance = new LoggedIn();

    LoggedIn(boolean x, String y){
        LoggedIn.isUserLoggedIn = x;
        LoggedIn.userID = y;
    }
    LoggedIn(){
        LoggedIn.isUserLoggedIn = false;
        LoggedIn.userID = "";
    }

    public static LoggedIn getInstance() {
        return instance;
    }

    public static boolean isLoggedIn() {
        return isUserLoggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        LoggedIn.isUserLoggedIn = loggedIn;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        LoggedIn.userID = userID;
    }


}