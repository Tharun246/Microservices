package com.gojo.microservices.product.Dto;


public class ProductResponse
{
    private long id;
    private String name;
    private String description;

    public ProductResponse() {
    }

    private double price;

    public ProductResponse(String name, long id, String description, double price) {
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
