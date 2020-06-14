package com.gabrielvital.covid_19cases.api;

import com.gabrielvital.covid_19cases.model.Casos;


import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface CasoService {

    @GET("data/?format=json&is_last=True&")
    Call <Casos> recuperarCasos  (@Query("city_ibge_code") String ibge);

}
