package com.example.recyclerview;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.homepage.FoodMenuApi;
import com.example.recyclerview.homepage.FoodMenuResponse;
import com.example.recyclerview.homepage.MenuFoodModel;
import com.example.recyclerview.homepage.foodMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private foodMenuAdapter adapter;
    private List<MenuFoodModel> menuList;
    private RecyclerView rvMyCardItem;
    ImageButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        rvMyCardItem = findViewById(R.id.rv_foodMenu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMyCardItem.setLayoutManager(gridLayoutManager);

        // Initialize the adapter with an empty list
        adapter = new foodMenuAdapter(new ArrayList<>());
        rvMyCardItem.setAdapter(adapter);

        // Fetch both menus
        fetchFoodMenu();
    }

    private void fetchFoodMenu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hushed-charming-clipper.glitch.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodMenuApi api = retrofit.create(FoodMenuApi.class);
        Call<FoodMenuResponse> call = api.getFoodMenu();

        call.enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuFoodModel> menuList = response.body().getFoodMenu();
                    adapter.updateData(menuList);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch food menu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
