package com.example.recyclerview.homepage;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodMenuApi {
    @GET("menu/food")
    Call<FoodMenuResponse> getFoodMenu();
}

