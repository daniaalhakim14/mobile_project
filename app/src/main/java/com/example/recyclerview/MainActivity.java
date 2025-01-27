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
import com.example.recyclerview.homepage.MenuDrinkModel;
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
    private RecyclerView rvMyCardItem;
    private ImageButton btn_add;

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
        adapter = new foodMenuAdapter();
        rvMyCardItem.setAdapter(adapter);

        // Fetch both food and drink menus
        fetchMenus();
    }

    private void fetchMenus() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hushed-charming-clipper.glitch.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodMenuApi api = retrofit.create(FoodMenuApi.class);

        // Fetch food menu
        api.getFoodMenu().enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuFoodModel> foodMenu = response.body().getFoodMenu();

                    // Fetch drink menu after food menu
                    fetchDrinkMenu(foodMenu);
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

    private void fetchDrinkMenu(List<MenuFoodModel> foodMenu) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hushed-charming-clipper.glitch.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodMenuApi api = retrofit.create(FoodMenuApi.class);

        // Fetch drink menu
        api.getDrinkMenu().enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MenuDrinkModel> drinkMenu = response.body().getDrinkMenu();

                    // Update adapter with both food and drink menus
                    adapter.updateData(foodMenu, drinkMenu);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch drink menu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
