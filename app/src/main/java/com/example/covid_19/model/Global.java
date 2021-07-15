package com.example.covid_19.model;

import com.google.gson.annotations.SerializedName;

public class Global {

    @SerializedName("NewConfirmed")
    private int newConfirmedCases;

    @SerializedName("TotalConfirmed")
    private int totalConfirmedCases;

    @SerializedName("NewDeaths")
    private int newDeathCases;

    @SerializedName("TotalDeaths")
    private int totalDeathCases;

    @SerializedName("NewRecovered")
    private int newRecoveredCases;

    @SerializedName("TotalRecovered")
    private int totalRecoveredCases;

    @SerializedName("Date")
    private String date;

    public Global(int newConfirmedCases, int totalConfirmedCases, int newDeathCases, int totalDeathCases, int newRecoverdCases, int totalRecoveredCases, String date) {
        this.newConfirmedCases = newConfirmedCases;
        this.totalConfirmedCases = totalConfirmedCases;
        this.newDeathCases = newDeathCases;
        this.totalDeathCases = totalDeathCases;
        this.newRecoveredCases = newRecoverdCases;
        this.totalRecoveredCases = totalRecoveredCases;
        this.date = date;
    }

    public Global() {
    }

    public int getNewConfirmedCases() {
        return newConfirmedCases;
    }

    public void setNewConfirmedCases(int newConfirmedCases) {
        this.newConfirmedCases = newConfirmedCases;
    }

    public int getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(int totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public int getNewDeathCases() {
        return newDeathCases;
    }

    public void setNewDeathCases(int newDeathCases) {
        this.newDeathCases = newDeathCases;
    }

    public int getTotalDeathCases() {
        return totalDeathCases;
    }

    public void setTotalDeathCases(int totalDeathCases) {
        this.totalDeathCases = totalDeathCases;
    }

    public int getNewRecoverdCases() {
        return newRecoveredCases;
    }

    public void setNewRecoverdCases(int newRecoverdCases) {
        this.newRecoveredCases = newRecoverdCases;
    }

    public int getTotalRecoveredCases() {
        return totalRecoveredCases;
    }

    public void setTotalRecoveredCases(int totalRecoveredCases) {
        this.totalRecoveredCases = totalRecoveredCases;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
