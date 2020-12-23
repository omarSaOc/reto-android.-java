package com.omar.retoandroid.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.omar.retoandroid.data.room.Visit;

import java.util.List;

@Dao
public interface VisitDao {

    @Query("SELECT * FROM visit")
    List<Visit> getVisits();

    @Insert
    void addVisit(Visit visit);

    @Delete
    void delete(Visit visit);

    @Update
    void updateVisit(Visit visit);

    @Query("SELECT * FROM visit WHERE id LIKE :id")
    Visit getVisit(String id);
}
