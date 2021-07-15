package com.example.covid_19.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class RegionalData {

    @SerializedName("loc")
    private String stateName;
    private int confirmedCasesIndian;
    private int discharged;
    private int deaths;
    private int totalConfirmed;

    public RegionalData(String stateName, int confirmedCasesIndian, int discharged, int deaths, int totalConfirmed) {
        this.stateName = stateName;
        this.confirmedCasesIndian = confirmedCasesIndian;
        this.discharged = discharged;
        this.deaths = deaths;
        this.totalConfirmed = totalConfirmed;
    }

    public RegionalData() {
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getConfirmedCasesIndian() {
        return confirmedCasesIndian;
    }

    public void setConfirmedCasesIndian(int confirmedCasesIndian) {
        this.confirmedCasesIndian = confirmedCasesIndian;
    }

    public int getDischarged() {
        return discharged;
    }

    public void setDischarged(int discharged) {
        this.discharged = discharged;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public static Comparator<RegionalData> regionalDataComparator = (regionalData, t1)
            -> t1.getConfirmedCasesIndian() - regionalData.getConfirmedCasesIndian();
}
