package com.omar.retoandroid.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Visit.class}, version = 1)
public abstract  class VisitDataBase extends RoomDatabase {
    public abstract VisitDao getVisitDao();
}
