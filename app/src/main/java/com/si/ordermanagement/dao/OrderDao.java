package com.si.ordermanagement.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.si.ordermanagement.model.OrderData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderDao {


    @Query("SELECT * FROM `order`")
    List<OrderData> getAllOrder();

    @Query("SELECT * FROM `order` WHERE id=:id")
    OrderData getOrder(int id);

    @Insert
    void insertAll(OrderData orderData);

    @Update
    void update(OrderData orderData);

    @Delete
    void delete(OrderData orderData);
}
