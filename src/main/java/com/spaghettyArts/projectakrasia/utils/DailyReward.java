package com.spaghettyArts.projectakrasia.utils;

public class DailyReward {

    public static int getDaily(int day) {
        if (day < 7) {
            return day * 100;
        } else {
            return 700;
        }
    }
}
