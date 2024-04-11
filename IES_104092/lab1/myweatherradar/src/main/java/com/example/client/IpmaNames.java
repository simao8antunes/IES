package com.example.client;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IpmaNames {
    
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("data")
    @Expose
    private List<IpmaData> data = null;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<IpmaData> getData() {
        return data;
    }

    public void setData(List<IpmaData> data) {
        this.data = data;
    }
}