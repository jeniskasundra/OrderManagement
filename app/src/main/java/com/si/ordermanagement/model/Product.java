
package com.si.ordermanagement.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("dis")
    @Expose
    private ArrayList<String> dis = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDis() {
        return dis;
    }

    public void setDis(ArrayList<String> dis) {
        this.dis = dis;
    }

    @Override
    public String toString() {
        return name;
    }

}
