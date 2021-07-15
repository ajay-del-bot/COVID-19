package com.example.covid_19.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.JsonApiHolder;
import com.example.covid_19.local.StateDAO;
import com.example.covid_19.local.StateDatabase;
import com.example.covid_19.local.StateEntity;
import com.example.covid_19.model.CountriesModel;
import com.example.covid_19.model.GlobalCasesModel;
import com.example.covid_19.model.LatestCaseModel;
import com.example.covid_19.R;
import com.example.covid_19.model.RegionalData;
import com.example.covid_19.adapter.DetailAdapter;
import com.example.covid_19.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView textViewTitle;
    private TextView textViewTime;
    private ProgressBar progressBar;
    private ImageView imageViewBack;

    private JsonApiHolder jsonApiHolder;
    private List<RegionalData> stateWiseCaseList = new ArrayList<>();
    private List<CountriesModel> countriesWiseCaseList = new ArrayList<>();

    private StateDatabase stateDatabase;
    private StateDAO stateDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setView();
        setListeners();

        stateDatabase = StateDatabase.getInstance(this);
        stateDao = stateDatabase.stateDAO();
        //getLatestStateCases();
        getLatestCountryCases();
    }

    private void setView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        textViewTitle = findViewById(R.id.textViewTitleDetail);
        textViewTime = findViewById(R.id.textViewTimeDetail);
        progressBar = findViewById(R.id.progressBar);
        imageViewBack = findViewById(R.id.imageViewBack);

//        List<String> stateList = new ArrayList<>();
//        stateList.add("Uttar Pradesh");
//        stateList.add("Delhi");
//        stateList.add("Rajasthan");
//        stateList.add("Goa");
//        stateList.add("Maharashtra");

//        StateAdapter stateAdapter = new StateAdapter(stateList);
//        recyclerView.setAdapter(stateAdapter);
    }

    private void setListeners() {
        imageViewBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void getLatestStateCases(){

        textViewTitle.setText("State Data (All)");

        jsonApiHolder = getRetrofitInstance(Constants.BASE_LOCAL_URL).create(JsonApiHolder.class);
        Call<LatestCaseModel> latestCaseModelCall = jsonApiHolder.getLatestCases();

        latestCaseModelCall.enqueue(new Callback<LatestCaseModel>() {
            @Override
            public void onResponse(Call<LatestCaseModel> call, Response<LatestCaseModel> response) {
                if(response.isSuccessful()){
                    stateWiseCaseList.addAll(response.body().getData().getRegional());
                    progressBar.setVisibility(View.GONE);
                    DetailAdapter detailAdapter = new DetailAdapter(stateWiseCaseList);
                    recyclerView.setAdapter(detailAdapter);
//                    detailAdapter.setOnItemClickListener(new DetailAdapter.OnItemClickListener() {
//                      @Override
//                        public void onItemClick(){
//                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
//                            startActivity(intent);
//                      }
//                    });
                    saveInDb();
                }
                else{
                    Toast.makeText(DetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LatestCaseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void getLatestCountryCases(){

        textViewTitle.setText("Countries Data (All)");

        jsonApiHolder = getRetrofitInstance(Constants.BASE_GLOBAL_URL).create(JsonApiHolder.class);
        Call<GlobalCasesModel> globalCasesModelCall = jsonApiHolder.getGlobalCases();

        globalCasesModelCall.enqueue(new Callback<GlobalCasesModel>() {
            @Override
            public void onResponse(Call<GlobalCasesModel> call, Response<GlobalCasesModel> response) {
                if(response.isSuccessful()){
                    GlobalCasesModel globalCasesModel = response.body();
                    assert globalCasesModel != null;
                    countriesWiseCaseList.addAll(globalCasesModel.getCountries());

                    progressBar.setVisibility(View.GONE);
                    if(globalCasesModel.getGlobal().getDate().substring(11, 16)!=null)
                        textViewTime.setText("Last updated at " +
                            globalCasesModel.getGlobal().getDate().substring(11, 16));

                    DetailAdapter detailAdapter = new DetailAdapter(countriesWiseCaseList, true);
                    recyclerView.setAdapter(detailAdapter);
//                    detailAdapter.setOnItemClickListener(new DetailAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(){
//                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    });
                   // saveInDb();
                }
                else{
                    Toast.makeText(DetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GlobalCasesModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

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

    private void saveInDb() {
        for(RegionalData regionalData : stateWiseCaseList) {
            StateEntity stateEntity = new StateEntity(regionalData.getStateName(),
                    regionalData.getTotalConfirmed(),
                    regionalData.getDischarged(),
                    regionalData.getDeaths(),
                    regionalData.getTotalConfirmed() - regionalData.getDischarged() - regionalData.getDeaths());

            stateDao.insert(stateEntity);
        }
    }
}