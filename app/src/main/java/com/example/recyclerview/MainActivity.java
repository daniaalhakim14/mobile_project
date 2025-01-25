package com.example.recyclerview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private foodMenuAdapter adapter;
    private List<Menu> menuList;
    private RecyclerView rvMyCardItem; // Declare RecyclerView as a class-level field
    ImageButton btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        rvMyCardItem = findViewById(R.id.rv_foodMenu);
        // Set GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMyCardItem.setLayoutManager(gridLayoutManager);
        btn_add = findViewById(R.id.imagebtn_add_to_cart);

        // Initialize the adapter with an empty list
        adapter = new foodMenuAdapter(new ArrayList<>());
        rvMyCardItem.setAdapter(adapter);
        fetchFoodMenu();


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }



    private void fetchFoodMenu() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hushed-charming-clipper.glitch.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FoodMenuApi api = retrofit.create(FoodMenuApi.class);

        long startTime = System.currentTimeMillis();

        Call<FoodMenuResponse> call = api.getFoodMenu();

        call.enqueue(new Callback<FoodMenuResponse>() {
            @Override
            public void onResponse(Call<FoodMenuResponse> call, Response<FoodMenuResponse> response) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                Log.d("API_RESPONSE_TIME", "Elapsed time: " + elapsedTime + "ms");

                if (response.isSuccessful() && response.body() != null) {
                    // Get the foodMenu list from the response object
                    List<Menu> menuList = response.body().getFoodMenu();

                    // Update the adapter's data
                    adapter.updateData(menuList);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodMenuResponse> call, Throwable t) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                Log.d("API_RESPONSE_TIME", "Elapsed time: " + elapsedTime + "ms");

                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
