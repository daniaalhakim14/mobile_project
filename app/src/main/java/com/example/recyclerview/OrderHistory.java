package com.example.recyclerview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview.OrderAdapter;
import com.example.recyclerview.R;
import com.example.recyclerview.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {

    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private Handler handler = new Handler();
    private static final String CHANNEL_ID = "OrderStatusChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize RecyclerView
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter and set it to RecyclerView
        orderAdapter = new OrderAdapter(orderList, this);
        orderRecyclerView.setAdapter(orderAdapter);

        // Check if an order is passed from add_to_cart
        Order newOrder = (Order) getIntent().getSerializableExtra("order");
        if (newOrder != null) {
            orderList.add(newOrder);
            orderAdapter.notifyDataSetChanged();

            // Change status to "completed" after 7 seconds and show notification
            changeOrderStatusAfterDelay(newOrder);
        }
    }

    private void changeOrderStatusAfterDelay(Order order) {
        handler.postDelayed(() -> {
            order.setStatus("completed");
            orderAdapter.notifyDataSetChanged(); // Update the UI
            showNotification(); // Show a notification
        }, 7000); // 7 seconds delay
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Status Channel";
            String description = "Channel for order status notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        // Build and display the notification
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Order Status")
                    .setContentText("Your order is ready!")
                    .setSmallIcon(android.R.drawable.ic_notification_overlay)
                    .build();
        }

        notificationManager.notify(0, notification);
    }
}
