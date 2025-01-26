package com.example.recyclerview.models;

import java.io.Serializable;

public class Order implements Serializable {
    private long orderId;
    private int customerId;
    private double totalPrice;
    private String status;
    private String orderDate;

    public Order(long orderId, int customerId, double totalPrice, String status, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
