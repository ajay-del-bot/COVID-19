package com.example.covid_19.model;

public class LatestCaseModel {

//    private boolean success;
    private Data data;
    private String lastRefreshed;

    public LatestCaseModel(Data data, String lastRefreshed) {
        this.data = data;
        this.lastRefreshed = lastRefreshed;
    }

    public LatestCaseModel() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(String lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }
}
