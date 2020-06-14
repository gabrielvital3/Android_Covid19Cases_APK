package com.gabrielvital.covid_19cases.api;

import com.gabrielvital.covid_19cases.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {


    @GET("{cep}/json/")
    Call <CEP> carregarCep(@Path("cep") String cep );

}
