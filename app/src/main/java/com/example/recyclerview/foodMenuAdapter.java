package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class foodMenuAdapter extends RecyclerView.Adapter<foodMenuAdapter.ViewHolder> {

    private List<Menu> menuList;

    public foodMenuAdapter(List<Menu> menuList) {
        this.menuList = menuList;
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
        holder.iv_item_image.setImageResource(menu.getFoodImage());
        holder.tv_item_name.setText(menu.getFoodName());
        holder.tv_item_price.setText(menu.getFoodPrice());
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


