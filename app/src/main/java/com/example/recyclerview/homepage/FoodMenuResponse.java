package com.example.recyclerview.homepage;

import java.util.List;

public class FoodMenuResponse {
    private List<MenuFoodModel> foodMenu;


    public List<MenuFoodModel> getFoodMenu() {
        return foodMenu;
    }



    public void setFoodMenu(List<MenuFoodModel> foodMenu) {
        this.foodMenu = foodMenu;
    }
}


