package com.example.recyclerview.homepage;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recyclerview.R;

import java.util.List;

public class foodMenuAdapter extends RecyclerView.Adapter<foodMenuAdapter.ViewHolder> {

    private List<Object> menuList; // Use Object to handle both food and drink items

    public foodMenuAdapter(List<Object> menuList) {
        this.menuList = menuList;
    }

    public void updateData(List<MenuFoodModel> newMenuList) {
        if (newMenuList != null && !newMenuList.isEmpty()) {
            this.menuList.clear();
            this.menuList.addAll(newMenuList);
            notifyDataSetChanged();
        } else {
            Log.e("Adapter", "Received empty or null menu list");
        }
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
        Object item = menuList.get(position);

        if (item instanceof MenuFoodModel) {
            MenuFoodModel food = (MenuFoodModel) item;
            holder.tv_item_name.setText(food.getFoodName());
            holder.tv_item_price.setText(String.format("RM %.2f", food.getFoodPrice()));

            Glide.with(holder.itemView.getContext())
                    .load(food.getImageURL())
                    .placeholder(R.drawable.ic_picture_background)
                    .error(R.drawable.ic_error_background)
                    .into(holder.iv_item_image);

            holder.imagebtn_add_to_cart.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), FoodDrinks_Detail.class);
                intent.putExtra("foodId", food.getFoodID());
                intent.putExtra("foodName", food.getFoodName());
                intent.putExtra("foodPrice", food.getFoodPrice());
                intent.putExtra("foodDescription", food.getFoodDescription());
                intent.putExtra("imageURL", food.getImageURL());
                v.getContext().startActivity(intent);
            });

        } else if (item instanceof MenuDrinkModel) {
            MenuDrinkModel drink = (MenuDrinkModel) item;
            holder.tv_item_name.setText(drink.getDrinkName());
            holder.tv_item_price.setText(String.format("RM %.2f", drink.getDrinkPrice()));

            Glide.with(holder.itemView.getContext())
                    .load(drink.getImageURL())
                    .placeholder(R.drawable.ic_picture_background)
                    .error(R.drawable.ic_error_background)
                    .into(holder.iv_item_image);

            holder.imagebtn_add_to_cart.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), FoodDrinks_Detail.class);
                intent.putExtra("foodId", drink.getDrinkID());
                intent.putExtra("foodName", drink.getDrinkName());
                intent.putExtra("foodPrice", drink.getDrinkPrice());
                intent.putExtra("foodDescription", drink.getDrinkDescription());
                intent.putExtra("imageURL", drink.getImageURL());
                v.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_item_image;
        TextView tv_item_name, tv_item_price;
        ImageButton imagebtn_add_to_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_item_image = itemView.findViewById(R.id.iv_item_image);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
            imagebtn_add_to_cart = itemView.findViewById(R.id.imagebtn_add_to_cart);
        }
    }
}

