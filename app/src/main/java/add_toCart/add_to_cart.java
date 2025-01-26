package add_toCart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;
import com.example.recyclerview.models.Order;

import java.util.List;

public class add_to_cart extends AppCompatActivity {

    private RecyclerView cartView;
    private TextView totalFeeTxt, taxTxt, totalTxt, emptyTxt;
    private double taxPercentage = 0.06; // Example tax percentage (6%)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

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
        TextView checkoutButton = findViewById(R.id.Checkout);
        checkoutButton.setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty. Please add items before checkout.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Calculate total item cost
            double totalFee = 0;
            for (CartItem item : cartItems) {
                totalFee += item.getPrice() * item.getQuantity();
            }

            // Calculate tax and total cost
            double tax = totalFee * taxPercentage;
            double totalCost = totalFee + tax;

            // Create an Order object with totalCost
            Order order = new Order(System.currentTimeMillis(), 101, totalCost, "pending", "2025-01-26");

            // Pass the order to OrderHistory via intent
            Intent intent = new Intent(add_to_cart.this, com.example.recyclerview.OrderHistory.class);
            intent.putExtra("order", order);
            startActivity(intent);

            // Clear the cart
            CartManager.getInstance().clearCart();
            cartItems.clear(); // Clear local cart items list
            updateTotals();
            emptyTxt.setVisibility(TextView.VISIBLE);
            cartView.setVisibility(RecyclerView.GONE);
            Toast.makeText(this, "Order placed successfully! Cart is now empty.", Toast.LENGTH_SHORT).show();
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
