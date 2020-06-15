package com.gabrielvital.covid_19_brazil_cases.model;

import java.util.Arrays;

public class Respostas {

    private String count;
    private Casos[] results;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Casos[] getResults() {
        return results;
    }

    public void setResults(Casos[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Respostas{" +
                "results=" + Arrays.toString(results) +
                '}';
    }
}
