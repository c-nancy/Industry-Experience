package com.iteration1.savingwildlife.entities;

import java.io.Serializable;

public class Beach implements Serializable {
    private String name;
    private String description;
    private String banner;

    public Beach(String name, String description, String banner) {
        this.name = name;
        this.description = description;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
