package com.example.recyclerview;

import java.util.List;

public class Menu {
    private int foodID;
    private String foodName;
    private String foodDescription;
    private double foodPrice;
    private FoodImage foodImage;

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public FoodImage getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(FoodImage foodImage) {
        this.foodImage = foodImage;
    }
}


