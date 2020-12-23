package com.omar.retoandroid.retrofit;

import com.omar.retoandroid.Util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitClient {

    private static VisitClient instance;
    private VisitService visitService;
    private Retrofit retrofit;

    public VisitClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        visitService = retrofit.create(VisitService.class);
    }

    public static VisitClient getInstance(){
        if (instance == null){
            instance = new VisitClient();
        }
        return  instance;
    }

    public VisitService getVisitService(){
        return visitService;
    }

}
