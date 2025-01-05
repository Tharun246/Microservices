package com.example.Order_Service.Dto;

public class OrderLineItemsDto
{
    private Long id;

    private String skuCode;

    private double price;

    private int quantity;

    public OrderLineItemsDto(Long id, String skuCode, double price, int quantity) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderLineItemsDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
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
