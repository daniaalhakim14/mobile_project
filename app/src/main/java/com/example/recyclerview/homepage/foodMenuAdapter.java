package com.example.recyclerview.homepage;

import android.content.Intent;
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

    private List<Menu> menuList;

    public foodMenuAdapter(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public void updateData(List<Menu> newMenuList) {
        this.menuList.clear();
        this.menuList.addAll(newMenuList);
        notifyDataSetChanged();
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

        holder.tv_item_name.setText(menu.getFoodName());
        holder.tv_item_price.setText(String.format("RM %.2f", menu.getFoodPrice()));

        Glide.with(holder.itemView.getContext())
                .load(menu.getImageURL())
                .placeholder(R.drawable.ic_picture_background)
                .error(R.drawable.ic_error_background)
                .into(holder.iv_item_image);

        holder.imagebtn_add_to_cart.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FoodDrinks_Detail.class);
            intent.putExtra("foodId", menu.getFoodID());
            intent.putExtra("foodName", menu.getFoodName());
            intent.putExtra("foodPrice", menu.getFoodPrice());
            intent.putExtra("foodDescription", menu.getFoodDescription());
            intent.putExtra("imageURL", menu.getImageURL());
            v.getContext().startActivity(intent);
        });
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
