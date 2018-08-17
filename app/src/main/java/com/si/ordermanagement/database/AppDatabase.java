package com.si.ordermanagement.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.si.ordermanagement.dao.OrderDao;
import com.si.ordermanagement.model.OrderData;


@Database(entities = {OrderData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "app_db";
    public abstract OrderDao orderDao();
}
