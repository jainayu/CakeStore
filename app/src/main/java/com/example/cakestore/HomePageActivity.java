package com.example.cakestore;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.cakestore.adapters.CakeAdapter;
import com.example.cakestore.models.CakeDataModel;
import com.example.cakestore.models.CakeResponse;
import com.example.cakestore.networking.RetrofitClient;

import java.io.Serializable;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    List<CakeDataModel> cakesList;
    GridView gridViewCakes;
    CakeAdapter cakeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        gridViewCakes = findViewById(R.id.gridViewCakes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fetchData();
    }

    private void fetchData(){
        Call<CakeResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .cakeDetails();

        call.enqueue(new Callback<CakeResponse>() {
            @Override
            public void onResponse(Call<CakeResponse> call, Response<CakeResponse> response) {
                CakeResponse cakeResponse = response.body();
                if(response.code() == 200){
                    if(cakeResponse.getData() != null) {
                        cakesList = cakeResponse.getData();
                        cakeAdapter = new CakeAdapter(getApplicationContext(), R.layout.item_cake_details, cakesList, new CakeAdapter.CakeDetailsListener() {
                            @Override
                            public void AddToCartOnClick(View view, int position) {
                                Intent intent = new Intent(getBaseContext(), AddToCartActivity.class);
                                startActivity(intent);
                            }
                        });
                        gridViewCakes.setAdapter(cakeAdapter);
                    }
                    else{
                        System.out.println(cakeResponse.getStatus());
                        System.out.println(cakeResponse.getError_msg());
                    }
                }
                else if (response.code() == 422){

                }
            }

            @Override
            public void onFailure(Call<CakeResponse> call, Throwable t) {

            }
        });
    }
}
