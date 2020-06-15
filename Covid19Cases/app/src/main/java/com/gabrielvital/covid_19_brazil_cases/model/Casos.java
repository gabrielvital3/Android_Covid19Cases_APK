package com.gabrielvital.covid_19_brazil_cases.model;

public class Casos extends Respostas {

    private String city;
    private String confirmed;
    private String deaths;
    private String estimated_population_2019;
    private String state;
    private String date;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConfirmed() { return confirmed; }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getEstimated_population_2019() {
        return estimated_population_2019;
    }

    public void setEstimated_population_2019(String estimated_population_2019) {
        this.estimated_population_2019 = estimated_population_2019;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
