package com.iteration1.savingwildlife.entities;

import java.io.Serializable;

public class Beach implements Serializable {
    private String name;
    private String description;
    private String infoimg;
    private String banner;

    public Beach(String name, String description, String infoimg, String banner) {
        this.name = name;
        this.description = description;
        this.infoimg = infoimg;
        this.banner = banner;
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

    public String getInfoimg() {
        return infoimg;
    }

    public void setInfoimg(String infoimg) {
        this.infoimg = infoimg;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
