package com.example.recyclerview;

public class Menu {
    private int FoodId;
    private String FoodName;
    private String FoodPrice;
    private int FoodImage;

    public Menu(int foodId, String foodName, String foodPrice, int foodImage) {
        this.FoodId = foodId;
        this.FoodName = foodName;
        this.FoodPrice = foodPrice;
        this.FoodImage = foodImage;
    }


    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        this.FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        this.FoodName = foodName;
    }

    public int getFoodImage() {
        return FoodImage;
    }

    public void setFoodImage(int foodImage) {
        this.FoodImage = foodImage;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.FoodPrice = foodPrice;
    }
}
