package com.example.Order_Service.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_line_items")
public class OrderLineItems
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String skuCode;

    private double price;

    private int quantity;

    public OrderLineItems(long id, String skuCode, double price, int quantity) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderLineItems() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
