package com.example.findaseatfinal2;

import java.util.HashMap;

public class Day {
    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;

    public static final String[] Days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public static final HashMap<String, Integer> DAY_MAP = new HashMap<>();

    static {
        DAY_MAP.put("Sunday", 0);
        DAY_MAP.put("Monday", 1);
        DAY_MAP.put("Tuesday", 2);
        DAY_MAP.put("Wednesday", 3);
        DAY_MAP.put("Thursday", 4);
        DAY_MAP.put("Friday", 5);
        DAY_MAP.put("Saturday",6);
    }

    public static int getDay(String day) {
        return DAY_MAP.get(day);
    }
}
