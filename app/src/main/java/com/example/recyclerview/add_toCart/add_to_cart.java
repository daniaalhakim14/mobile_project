package com.example.recyclerview.add_toCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.DatabaseHelper.DatabaseHelper;
import com.example.recyclerview.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class add_to_cart extends AppCompatActivity {

    private RecyclerView cartView;
    private TextView totalFeeTxt, taxTxt, totalTxt, emptyTxt;
    private double taxPercentage = 0.06; // Example tax percentage (6%)
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Bind UI components
        cartView = findViewById(R.id.cartView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);

        // Retrieve cart items from CartManager
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        if (cartItems.isEmpty()) {
            emptyTxt.setVisibility(TextView.VISIBLE);
            cartView.setVisibility(RecyclerView.GONE);
        } else {
            emptyTxt.setVisibility(TextView.GONE);
            cartView.setVisibility(RecyclerView.VISIBLE);

            // Set up RecyclerView with CartAdapter
            cartView.setLayoutManager(new LinearLayoutManager(this));
            CartAdapter adapter = new CartAdapter(cartItems, this::updateTotals);
            cartView.setAdapter(adapter);

            // Calculate and display totals initially
            updateTotals();
        }

        // Checkout button logic
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        TextView checkoutButton = findViewById(R.id.Checkout);
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty. Please add items before checkout.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate total item cost
            double totalFee = 0;
            StringBuilder quantityBuilder = new StringBuilder();

            for (CartItem item : cartItems) {
                totalFee += item.getPrice() * item.getQuantity();
                quantityBuilder.append(item.getQuantity()).append("x ").append(item.getName()).append(", ");
            }

            // Calculate tax and total cost
            double tax = totalFee * taxPercentage;
            double totalCost = totalFee + tax;

            // Insert the order into the database
            boolean isInserted = dbHelper.insertOrder(
                    "101", // Example customer ID
                    String.format("%.2f", totalCost),
                    "pending", // Order status
                    quantityBuilder.toString().trim(),
                    currentDate // Current date
            );
            if (isInserted) {
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();

                // Pass the order to OrderHistory via intent
                Intent intent = new Intent(add_to_cart.this, com.example.recyclerview.OrderHistory.class);
                startActivity(intent);

                // Clear the cart
                CartManager.getInstance().clearCart();
                cartItems.clear(); // Clear local cart items list
                updateTotals();
                emptyTxt.setVisibility(TextView.VISIBLE);
                cartView.setVisibility(RecyclerView.GONE);
            } else {
                Toast.makeText(this, "Failed to place the order. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Update totals dynamically
    private void updateTotals() {
        double totalFee = 0;

        // Retrieve updated cart items from CartManager
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        for (CartItem item : cartItems) {
            totalFee += item.getPrice() * item.getQuantity();
        }

        // Calculate tax and total cost
        double tax = totalFee * taxPercentage;
        double totalCost = totalFee + tax;

        // Update UI
        totalFeeTxt.setText(String.format("RM %.2f", totalFee));
        taxTxt.setText(String.format("RM %.2f", tax));
        totalTxt.setText(String.format("RM %.2f", totalCost));

        // Hide cart if it becomes empty
        if (cartItems.isEmpty()) {
            emptyTxt.setVisibility(TextView.VISIBLE);
            cartView.setVisibility(RecyclerView.GONE);
        } else {
            emptyTxt.setVisibility(TextView.GONE);
            cartView.setVisibility(View.VISIBLE);
        }
    }
}
