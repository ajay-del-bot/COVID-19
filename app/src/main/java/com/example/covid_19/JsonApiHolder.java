package com.example.covid_19;

import com.example.covid_19.model.GlobalCasesModel;
import com.example.covid_19.model.LatestCaseModel;
import com.example.covid_19.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonApiHolder {


    @GET("/covid19-in/stats/latest")
    Call<LatestCaseModel> getLatestCases();

    @GET("/summary")
    Call<GlobalCasesModel> getGlobalCases();


}
