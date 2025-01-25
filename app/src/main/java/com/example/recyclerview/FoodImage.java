package com.example.recyclerview;

import java.util.List;

public class FoodImage {
    private String type;
    private List<Integer> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    // Method to convert List<Integer> to byte[]
    public byte[] getImageDataAsByteArray() {
        byte[] byteArray = new byte[data.size()];
        for (int i = 0; i < data.size(); i++) {
            byteArray[i] = data.get(i).byteValue();
        }
        return byteArray;
    }
}

