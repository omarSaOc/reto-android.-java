package com.omar.retoandroid.data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.omar.retoandroid.data.room.Visit;
import com.omar.retoandroid.data.room.VisitLab;
import com.omar.retoandroid.retrofit.VisitClient;
import com.omar.retoandroid.retrofit.VisitService;
import com.omar.retoandroid.retrofit.response.Location;
import com.omar.retoandroid.retrofit.response.VisitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitRepository {
    private Context context;
    private VisitLab visitLab;
    private List<Visit> visitList;
    private  MutableLiveData<List<Visit>> visitsObserver = new MutableLiveData<>();
    private VisitClient client;
    private VisitService service;


    public VisitRepository(Context context){
        this.context = context;
        visitLab = VisitLab.getInstance(context);
        visitList = visitLab.getVisits();
        if (visitList.size() == 0){
            client = VisitClient.getInstance();
            service = client.getVisitService();
            getAllVisits();
        }
    }

    private void getAllVisits(){
        Call<List<VisitResponse>> call =service.getAllVisits();
        call.enqueue(new Callback<List<VisitResponse>>() {
            @Override
            public void onResponse(Call<List<VisitResponse>> call, Response<List<VisitResponse>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        storeVisit(response.body());
                    }
                }else{
                    Log.e("ERROR","ERROR EN LA CONSULTA");
                }
            }

            @Override
            public void onFailure(Call<List<VisitResponse>> call, Throwable t) {
                Log.e("ERROR_ALL_VISIT", t.getMessage());
            }
        });
    }

    private void storeVisit(List<VisitResponse> visitResponseList){
        int i;
        for (i=0;i<visitResponseList.size();i++){
            VisitResponse visitResponse = visitResponseList.get(i);
            Location location = visitResponse.getLocation();
            Visit visit = new Visit();
            visit.setStreetName(visitResponse.getStreetName());
            visit.setSuburb(visitResponse.getSuburb());
            visit.setStatus(visitResponse.getVisited());
            visit.setLatitude(location.getLatitude());
            visit.setLongitude(location.getLongitude());
            visitLab.addVisit(visit);
        }
        visitList = visitLab.getVisits();
        visitsObserver.setValue(visitList);
    }

    public void updateVisit(String id){
        Visit visit = visitLab.getVisit(id);
        visit.setStatus(true);
        visitLab.updateVisit(visit);
        visitList = visitLab.getVisits();
        visitsObserver.setValue(visitList);
    }

    public LiveData<List<Visit>> getVisits(){
        visitList = visitLab.getVisits();
        visitsObserver.setValue(visitList);
        return visitsObserver;
    }

}
