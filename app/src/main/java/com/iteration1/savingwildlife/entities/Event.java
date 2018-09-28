package com.iteration1.savingwildlife.entities;

import java.io.Serializable;

public class Event implements Serializable{
    private String event_type;
    private String event_date;
    private String event_location;
    private String event_start;
    private String event_end;
    private String imei;
    private String name;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Event() {
    }

    public Event(String event_type, String event_date, String event_location, String event_start, String event_end, String imei, String name) {
        this.event_type = event_type;
        this.event_date = event_date;
        this.event_location = event_location;
        this.event_start = event_start;
        this.event_end = event_end;

        this.imei = imei;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_start() {
        return event_start;
    }

    public void setEvent_start(String event_start) {
        this.event_start = event_start;
    }

    public String getEvent_end() {
        return event_end;
    }

    public void setEvent_end(String event_end) {
        this.event_end = event_end;
    }
}
