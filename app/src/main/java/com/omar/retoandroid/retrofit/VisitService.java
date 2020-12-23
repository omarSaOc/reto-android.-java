package com.omar.retoandroid.retrofit;

import com.omar.retoandroid.retrofit.response.VisitResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VisitService {

    @GET("interview")
    Call<List<VisitResponse>> getAllVisits();
}
