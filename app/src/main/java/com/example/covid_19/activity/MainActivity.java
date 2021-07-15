package com.example.covid_19.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.JsonApiHolder;
import com.example.covid_19.adapter.Top5Adapter;
import com.example.covid_19.model.Global;
import com.example.covid_19.model.GlobalCasesModel;
import com.example.covid_19.model.PostModel;
import com.example.covid_19.R;
import com.example.covid_19.model.LatestCaseModel;
import com.example.covid_19.model.Summary;
import com.example.covid_19.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private JsonApiHolder jsonApiHolder;
    private TabLayout tabLayout;
    private TextView textViewConfirmedCases;
    private TextView textViewActiveCases;
    private TextView textViewDischargedCases;
    private TextView textViewDeaths;

    private TextView textViewTime;
    private ProgressBar progressBar;

    private RecyclerView recyclerViewTop5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonApiHolder = getRetrofitInstance(Constants.BASE_LOCAL_URL).create(JsonApiHolder.class);

        setView();
        setClickListeners();
        fetchGlobalData();

    }

    private void setView() {
        tabLayout = findViewById(R.id.tabLayout);
        textViewConfirmedCases = findViewById(R.id.textViewConfirmedCases);
        textViewActiveCases = findViewById(R.id.textViewActiveCasesMain);
        textViewDischargedCases = findViewById(R.id.textViewDischargedCases);
        textViewDeaths = findViewById(R.id.textViewDeathCases);

        textViewTime = findViewById(R.id.textViewTimeMain);
        progressBar = findViewById(R.id.progressBarMain);
        recyclerViewTop5 = findViewById(R.id.recycler_view_top5);
        recyclerViewTop5.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        tabLayout.addTab(tabLayout.newTab().setText("Global"));
        tabLayout.addTab(tabLayout.newTab().setText("India"));
    }

    private void setClickListeners() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                progressBar.setVisibility(View.VISIBLE);
                if(tab.getPosition() == 0) {
                    fetchGlobalData();
                }
                else {
                    fetchCountryData();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void fetchGlobalData() {
        jsonApiHolder = getRetrofitInstance(Constants.BASE_GLOBAL_URL).create(JsonApiHolder.class);
        Call<GlobalCasesModel> globalCasesModelCall = jsonApiHolder.getGlobalCases();

        globalCasesModelCall.enqueue(new Callback<GlobalCasesModel>() {
            @Override
            public void onResponse(Call<GlobalCasesModel> call, Response<GlobalCasesModel> response) {
                if(response.isSuccessful()) {
                    Global global = response.body().getGlobal();

                    setGlobalTextView(global);
                    Top5Adapter top5Adapter = new Top5Adapter(response.body().getCountries(), true);
                    recyclerViewTop5.setAdapter(top5Adapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GlobalCasesModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void fetchCountryData() {
        jsonApiHolder = getRetrofitInstance(Constants.BASE_LOCAL_URL).create(JsonApiHolder.class);
        Call<LatestCaseModel> latestCasesModelCall = jsonApiHolder.getLatestCases();
        latestCasesModelCall.enqueue(new Callback<LatestCaseModel>() {

            @Override
            public void onResponse(Call<LatestCaseModel> call, Response<LatestCaseModel> response) {
                if(response.isSuccessful()) {
                    setCountryTextView(response.body().getData().getSummary());
                    textViewTime.setText("Last updated at " +
                            response.body().getLastRefreshed().substring(11, 16));
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(MainActivity.this, "Something went wrong!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LatestCaseModel> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }

    private void setCountryTextView(Summary summary) {
        textViewConfirmedCases.setText(String.valueOf(summary.getTotal()));
        textViewActiveCases.setText(String.valueOf(
                summary.getTotal() - summary.getDischarged() - summary.getDeaths()
        ));
        textViewDischargedCases.setText(String.valueOf(summary.getDischarged()));
        textViewDeaths.setText(String.valueOf(summary.getDeaths()));
    }

    private void setGlobalTextView(Global global) {
        textViewConfirmedCases.setText(String.valueOf(global.getTotalConfirmedCases()));
        textViewActiveCases.setText(String.valueOf(
                global.getTotalConfirmedCases() - global.getTotalRecoveredCases() - global.getTotalDeathCases()
        ));
        textViewDischargedCases.setText(String.valueOf(global.getTotalRecoveredCases()));
        textViewDeaths.setText(String.valueOf(global.getTotalDeathCases()));
        textViewTime.setText("Last updated at " + global.getDate().substring(11, 16));
    }

    private Retrofit getRetrofitInstance(String baseURL){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }


}