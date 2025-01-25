package com.example.recyclerview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private foodMenuAdapter adapter;
    private List<Menu> menuList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // to bind
        RecyclerView rvMyCardItem = findViewById(R.id.rv_foodMenu);
        // Set GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMyCardItem.setLayoutManager(gridLayoutManager);

        menuList = new ArrayList<>();
        menuList.add(new Menu(1,"nasi ayam","RM5.00",R.drawable.fire_cracker));
        menuList.add(new Menu(2,"nasi lemak","RM5.00",R.drawable.fire_cracker));
        menuList.add(new Menu(3,"nasi ayam","RM5.00",R.drawable.fire_cracker));
        menuList.add(new Menu(4,"nasi lemak","RM5.00",R.drawable.fire_cracker));
        menuList.add(new Menu(5,"nasi ayam","RM5.00",R.drawable.fire_cracker));
        menuList.add(new Menu(6,"nasi lemak","RM5.00",R.drawable.fire_cracker));
        menuList.add(new Menu(7,"nasi ayam","RM5.00",R.drawable.fire_cracker));


        foodMenuAdapter adapter = new foodMenuAdapter(menuList);
        rvMyCardItem.setAdapter(adapter);




    }
}