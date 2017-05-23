package com.zent.sleep.model;

import java.util.GregorianCalendar;

import io.realm.RealmObject;

/**
 * Created by Fabian Choi on 5/22/2017.
 * Represents a User
 */

public class User extends RealmObject {
    private String name;
    private int startSleepHour;
    private int startSleepMinute;
    private int endSleepHour;
    private int endSleepMinute;
    private boolean backgroundMusicEnabled;
    private boolean breathingEnabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartSleepHour() {
        return startSleepHour;
    }

    public void setStartSleepHour(int startSleepHour) {
        this.startSleepHour = startSleepHour;
    }

    public int getStartSleepMinute() {
        return startSleepMinute;
    }

    public void setStartSleepMinute(int startSleepMinute) {
        this.startSleepMinute = startSleepMinute;
    }

    public int getEndSleepHour() {
        return endSleepHour;
    }

    public void setEndSleepHour(int endSleepHour) {
        this.endSleepHour = endSleepHour;
    }

    public int getEndSleepMinute() {
        return endSleepMinute;
    }

    public void setEndSleepMinute(int endSleepMinute) {
        this.endSleepMinute = endSleepMinute;
    }

    public boolean isBackgroundMusicEnabled() {
        return backgroundMusicEnabled;
    }

    public void setBackgroundMusicEnabled(boolean backgroundMusicEnabled) {
        this.backgroundMusicEnabled = backgroundMusicEnabled;
    }

    public boolean isBreathingEnabled() {
        return breathingEnabled;
    }

    public void setBreathingEnabled(boolean breathingEnabled) {
        this.breathingEnabled = breathingEnabled;
    }
}
