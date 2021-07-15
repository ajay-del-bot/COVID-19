package com.example.covid_19.model;

import java.util.List;

public class Data {

    private List<RegionalData> regional;
    private Summary summary;

    public Data(Summary summary, List<RegionalData> regional) {
        this.summary = summary;
        this.regional = regional;
    }
    public Data() {
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<RegionalData> getRegional() {
        return regional;
    }

    public void setRegional(List<RegionalData> regional) {
        this.regional = regional;
    }
}
