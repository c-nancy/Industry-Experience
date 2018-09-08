package com.iteration1.savingwildlife.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Beach implements Parcelable {
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

    protected Beach(Parcel in) {
        name = in.readString();
        description = in.readString();
        banner = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
    }

    public static final Creator<Beach> CREATOR = new Creator<Beach>() {
        @Override
        public Beach createFromParcel(Parcel in) {
            return new Beach(in);
        }

        @Override
        public Beach[] newArray(int size) {
            return new Beach[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(banner);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
