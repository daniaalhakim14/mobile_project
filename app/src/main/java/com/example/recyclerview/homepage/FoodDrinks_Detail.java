package com.example.recyclerview.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.recyclerview.add_toCart.CartManager;
import com.example.recyclerview.add_toCart.CartItem;

import com.example.recyclerview.R;
import com.example.recyclerview.add_toCart.add_to_cart;

public class FoodDrinks_Detail extends AppCompatActivity {

    private int quantity = 1; // Default quantity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_drinks_detail);

        // Retrieve the data from the Intent
        Intent intent = getIntent();
        int foodId = intent.getIntExtra("foodId", -1);
        String foodName = intent.getStringExtra("foodName");
        double foodPrice = intent.getDoubleExtra("foodPrice", 0.0);
        String foodDescription = intent.getStringExtra("foodDescription");
        String imageURL = intent.getStringExtra("imageURL");

        // Bind UI elements
        TextView nameTextView = findViewById(R.id.tv_itemName);
        TextView priceTextView = findViewById(R.id.tv_price);
        TextView descriptionTextView = findViewById(R.id.tv_itemDescription);
        ImageView imageView = findViewById(R.id.iv_itemimage);
        ImageButton ibtn_add = findViewById(R.id.imagebtn_add_to_cart);
        ImageButton ibtn_minus = findViewById(R.id.ibtn_minus);
        TextView tv_quantity = findViewById(R.id.tv_quantity);
        Button btn_addorder = findViewById(R.id.btn_addOrder);

        // Set initial data
        nameTextView.setText(foodName);
        priceTextView.setText(String.format("RM %.2f", foodPrice));
        descriptionTextView.setText(foodDescription);
        tv_quantity.setText(String.valueOf(quantity));

        // Load the image using Glide
        Glide.with(this)
                .load(imageURL)
                .placeholder(R.drawable.ic_picture_background)
                .error(R.drawable.ic_error_background)
                .into(imageView);

        // Increase quantity
        ibtn_add.setOnClickListener(v -> {
            quantity++;
            tv_quantity.setText(String.valueOf(quantity));
        });

        // Decrease quantity (minimum is 1)
        ibtn_minus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tv_quantity.setText(String.valueOf(quantity));
            } else {
                Toast.makeText(this, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
            }
        });

        // Add to cart and navigate back to MainActivity
        btn_addorder.setOnClickListener(v -> {
            // Add the selected item to the cart
            CartManager.getInstance().addItem(new CartItem(foodId, foodName, foodDescription, foodPrice, imageURL, quantity));

            // Show a confirmation message
            Toast.makeText(this, foodName + " added to cart", Toast.LENGTH_SHORT).show();

            // Redirect to MainActivity
            Intent mainActivityIntent = new Intent(FoodDrinks_Detail.this, add_to_cart.class);
            startActivity(mainActivityIntent);

            // Close this activity
            finish();
        });
    }
}
