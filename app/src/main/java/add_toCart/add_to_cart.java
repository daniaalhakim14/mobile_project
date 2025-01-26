package add_toCart;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.R;

import java.util.List;

public class add_to_cart extends AppCompatActivity {

    private RecyclerView cartView;
    private TextView totalFeeTxt, taxTxt, totalTxt, emptyTxt;
    private double taxPercentage = 0.06; // Example tax percentage (6%)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
            // Show "empty cart" message if no items are in the cart
            emptyTxt.setVisibility(TextView.VISIBLE);
            cartView.setVisibility(RecyclerView.GONE);
        } else {
            // Hide "empty cart" message and show RecyclerView
            emptyTxt.setVisibility(TextView.GONE);
            cartView.setVisibility(RecyclerView.VISIBLE);

            // Set up RecyclerView
            cartView.setLayoutManager(new LinearLayoutManager(this));
            CartAdapter adapter = new CartAdapter(cartItems, this::updateTotals); // Pass the callback
            cartView.setAdapter(adapter);

            // Calculate and display totals initially
            updateTotals();
        }
    }

    // Update totals dynamically without requiring parameters
    private void updateTotals() {
        double totalFee = 0;

        // Retrieve cart items from CartManager
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();

        // Calculate total item cost
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
    }

}
