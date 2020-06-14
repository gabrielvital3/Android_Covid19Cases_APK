package com.gabrielvital.covid_19cases.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielvital.covid_19cases.R;
import com.gabrielvital.covid_19cases.api.CasoService;
import com.gabrielvital.covid_19cases.api.CepService;
import com.gabrielvital.covid_19cases.model.CEP;
import com.gabrielvital.covid_19cases.model.Casos;
import com.gabrielvital.covid_19cases.model.Respostas;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/*
Criado por Gabriel Vital Machioni de Oliveira
Fonte dos Dados: Secretarias Estaduais de Saúde/Consolidação por Brasil.IO
*/

public class MainActivity extends AppCompatActivity {

    private TextView numeroCasos;
    private TextView numeroMortes;
    private TextView nomeCidade;
    private TextView textData;
    private TextView textPop;
    private Respostas resposta;
    private CEP cepApi;
    private String ibge;
    private String Erro;

    private EditText cepText;
    private String cepUsuario;

    private Retrofit retrofit;
    private Retrofit retrofit2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button botaoCarregarDados = findViewById(R.id.botaoCarregar);
        numeroCasos = findViewById(R.id.textCasos);
        numeroMortes = findViewById(R.id.textViewMortos);
        nomeCidade = findViewById(R.id.textCidade);
        cepText = findViewById(R.id.textCEP);
        textData = findViewById(R.id.textViewData);
        textPop = findViewById(R.id.textViewPop);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://brasil.io/api/dataset/covid19/caso/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit2 = new  Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        botaoCarregarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cepUsuario = cepText.getText().toString();

                carregarCEP();

            }
        });

    }

    private void carregarCEP(){

        CepService cepService = retrofit2.create(CepService.class);
        Call <CEP> call = cepService.carregarCep(cepUsuario);

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Carregando...", Toast.LENGTH_SHORT).show();
                    cepApi = response.body();
                    Erro = cepApi.getErro();
                    if (Erro=="true"){
                        Toast.makeText(getApplicationContext(),"CEP inválido, tente novamente", Toast.LENGTH_LONG).show();
                    }
                    else {
                        ibge = cepApi.getIbge();
                        carregarListaCasos();
                    }
                } else Toast.makeText(getApplicationContext(),"CEP inválido, tente novamente", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"CEP inválido, tente novamente", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void carregarListaCasos(){

        CasoService casoService = retrofit.create(CasoService.class);
        Call <Casos> call = casoService.recuperarCasos(ibge);

       call.enqueue(new Callback<Casos>() {
           @Override
           public void onResponse(Call<Casos> call, Response<Casos> response) {
               // A respsota da api não é uma lista. mas o campo "results" é

               if (response.isSuccessful()){
                   resposta = response.body();
                   configurarSetText();
               } else Toast.makeText(getApplicationContext(),"Cidade não encontrada, tente novamente", Toast.LENGTH_LONG).show();


           }

           @Override
           public void onFailure(Call<Casos> call, Throwable t) {

               Toast.makeText(getApplicationContext(),"Cidade não encontrada, tente novamente", Toast.LENGTH_LONG).show();

           }
       });
    }

    @SuppressLint("SetTextI18n")
    private void configurarSetText(){

        Casos casos[] = resposta.getResults();            //configurando o model com informação das cidades (é um vetor)
        String nCasos = casos[0].getConfirmed();
        String nMortes = casos[0].getDeaths();
        String tamanhoPop = casos[0].getEstimated_population_2019();
        String data = casos[0].getDate();
        String estado = casos[0].getState();
        String cidade = casos[0].getCity();

        numeroCasos.setVisibility(View.VISIBLE);
        numeroCasos.setText("Há " + nCasos + " casos confirmados.");
        numeroMortes.setVisibility(View.VISIBLE);
        numeroMortes.setText("Há " + nMortes + " mortes confirmadas.");
        nomeCidade.setVisibility(View.VISIBLE);
        nomeCidade.setText("Cidade: " + cidade + "/" + estado);
        textPop.setVisibility(View.VISIBLE);
        textPop.setText("População: " + tamanhoPop + " habitantes.");
        textData.setVisibility(View.VISIBLE);
        textData.setText("Data: " + data);
    }

}
