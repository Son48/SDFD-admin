package com.example.sdfd_admin.weightloss;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class WeightLossModel implements Serializable {
    String name;
    String description;
    int calo;
    String img_url;
    String type;
    String time;

    @Exclude
    private String wlid;

    public String getWlid() {
        return wlid;
    }

    public void setWlid(String wlid) {
        this.wlid = wlid;
    }

    public WeightLossModel() {
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

    public int getCalo() {
        return calo;
    }

    public void setCalo(int calo) {
        this.calo = calo;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public WeightLossModel(String name, String description, int calo, String img_url, String type, String time) {
        this.name = name;
        this.description = description;
        this.calo = calo;
        this.img_url = img_url;
        this.type = type;
        this.time = time;
    }
}
