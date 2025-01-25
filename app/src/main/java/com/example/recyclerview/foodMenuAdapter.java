package com.example.recyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class foodMenuAdapter extends RecyclerView.Adapter<foodMenuAdapter.ViewHolder> {

    private List<Menu> menuList;

    public foodMenuAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    // Add this method to update the adapter's data
    public void updateData(List<Menu> newMenuList) {
        this.menuList.clear(); // Clear the old data
        this.menuList.addAll(newMenuList); // Add the new data
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_cart, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Menu menu = menuList.get(position);

        // Convert data to byte array and decode into a Bitmap
        byte[] imageData = menu.getFoodImage().getImageDataAsByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        // Set the Bitmap in the ImageView
        holder.iv_item_image.setImageBitmap(bitmap);
        // Set name and price
        holder.tv_item_name.setText(menu.getFoodName());
        holder.tv_item_price.setText(String.format("RM %.2f",menu.getFoodPrice()));
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_image;
        TextView tv_item_name, tv_item_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_item_image = itemView.findViewById(R.id.iv_item_image);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
        }
    }
}

