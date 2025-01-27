package com.example.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
    private Spinner itemTypeSpinner;

    // Lists to hold all data
    private List<MenuFoodModel> allFoodItems = new ArrayList<>();
    private List<MenuDrinkModel> allDrinkItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        rvMyCardItem = findViewById(R.id.rv_foodMenu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMyCardItem.setLayoutManager(gridLayoutManager);

        // Initialize the Spinner
        itemTypeSpinner = findViewById(R.id.item_type);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.items_type,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemTypeSpinner.setAdapter(spinnerAdapter);

        // Initialize the adapter with an empty list
        adapter = new foodMenuAdapter();
        rvMyCardItem.setAdapter(adapter);

        // Fetch both food and drink menus
        fetchMenus();

        // Set up Spinner selection logic
        setupSpinnerListener();
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
                    allFoodItems = response.body().getFoodMenu();

                    // Fetch drink menu after food menu
                    fetchDrinkMenu();
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

    private void fetchDrinkMenu() {
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
                    allDrinkItems = response.body().getDrinkMenu();

                    // Initially display all items
                    adapter.updateData(allFoodItems, allDrinkItems);
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

    private void setupSpinnerListener() {
        itemTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();

                // Filter based on the selected type
                switch (selectedType) {
                    case "All":
                        adapter.updateData(allFoodItems, allDrinkItems);
                        break;
                    case "Food":
                        adapter.updateData(allFoodItems, null); // Display only food
                        break;
                    case "Drinks":
                        adapter.updateData(null, allDrinkItems); // Display only drinks
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default to "All"
                adapter.updateData(allFoodItems, allDrinkItems);
            }
        });
    }
}
