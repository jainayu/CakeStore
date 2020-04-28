package com.example.cakestore.networking;

import com.example.cakestore.models.CakeResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;

public interface Api {

    @GET("actions.php?action=get_cakes&category=27")
    Call<CakeResponse> cakeDetails();
}
