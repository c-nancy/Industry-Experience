package com.iteration1.savingwildlife.entities;

import java.io.Serializable;

public class Beach implements Serializable {
    private String name;
    private String description;
    private String banner;
    private Double lat;
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Beach(String name, String description, String banner, Double lat, Double lng) {
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.lat = lat;
        this.lng = lng;
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
