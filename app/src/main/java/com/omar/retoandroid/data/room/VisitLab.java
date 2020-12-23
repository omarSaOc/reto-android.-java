package com.omar.retoandroid.data.room;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class VisitLab {

    private static VisitLab instance;

    private VisitDao visitDao;

    private VisitLab(Context context){
        Context appContext = context.getApplicationContext();
        VisitDataBase dataBase = Room.databaseBuilder(appContext, VisitDataBase.class, "visit")
                .allowMainThreadQueries()
                .build();
        visitDao = dataBase.getVisitDao();
    }

    public static VisitLab getInstance(Context context){
        if (instance == null){
            instance = new VisitLab(context);
        }
        return instance;
    }

    public List<Visit> getVisits(){
        return visitDao.getVisits();
    }

    public void addVisit(Visit visit){
        visitDao.addVisit(visit);
    }

    public void deleteVisit(Visit visit){
        visitDao.delete(visit);
    }

    public void updateVisit(Visit visit){
        visitDao.updateVisit(visit);
    }

    public Visit getVisit(String id){
        return visitDao.getVisit(id);
    }
}
