package com.iteration1.savingwildlife.entities;

import java.io.Serializable;

public class Beach implements Serializable {
    private String name;
    private String description;
    private String banner;
    private Double latitude;
    private Double longitude;


    public Beach(String name, String description, String banner, Double latitude, Double longitude) {
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Beach() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
