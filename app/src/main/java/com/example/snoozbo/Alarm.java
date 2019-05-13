package com.example.snoozbo;

public class Alarm {
    private int _snooze;

    public Alarm(){

    }

    public Alarm(int snooze) {

        this._snooze = snooze;
    }

    public void setSnooze(int snooze) {
        this._snooze = snooze;
    }
    public int getSnooze() {
        return this._snooze;
    }

}
