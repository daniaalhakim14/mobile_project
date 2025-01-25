package com.example.recyclerview;

import com.example.recyclerview.Menu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodMenuApi {
    @GET("menu/food")
    Call<FoodMenuResponse> getFoodMenu();
}

