package com.iteration1.savingwildlife.entities;

public class Event {
    private String event_type;
    private String event_date;
private String beach;

    public String getBeach() {
        return beach;
    }

    public void setBeach(String beach) {
        this.beach = beach;
    }

    public Event() {
    }

    public Event(String event_type, String event_date, String beach) {
        this.event_type = event_type;
        this.event_date = event_date;
        this.beach = beach;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_date() { return event_date ; }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }


}
