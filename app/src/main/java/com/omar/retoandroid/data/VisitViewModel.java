package com.omar.retoandroid.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.omar.retoandroid.data.room.Visit;

import java.util.List;

public class VisitViewModel extends AndroidViewModel {

//    private LiveData<List<VisitResponse>> visits;
    private LiveData<List<Visit>> storeVisit;
    private VisitRepository repository;

    public VisitViewModel(Application application){
        super(application);
        repository = new VisitRepository(application.getApplicationContext());
        storeVisit = repository.getVisits();
    }

    public LiveData<List<Visit>> getStoreVisits(){
        return storeVisit;
    }

    public void updateVisit(String id){
        repository.updateVisit(id);
    }
}
