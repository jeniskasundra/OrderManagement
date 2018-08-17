package com.si.ordermanagement.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "order")
public class OrderData {

    @ColumnInfo(name = "id")
    @PrimaryKey()
    private int id;
    @ColumnInfo(name = "pname")
    private String pname;
    @ColumnInfo(name = "pdis")
    private String pdis;
    @ColumnInfo(name = "qty")
    private String qty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdis() {
        return pdis;
    }

    public void setPdis(String pdis) {
        this.pdis = pdis;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
