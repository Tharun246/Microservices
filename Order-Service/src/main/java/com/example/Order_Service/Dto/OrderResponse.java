package com.example.Order_Service.Dto;

import java.util.List;

public class OrderResponse
{
    private Long id;

    private String orderNumber;

    private List<OrderLineItemsDto> orderLineItemsDto;

    public OrderResponse(long id, String orderNumber, List<OrderLineItemsDto> orderLineItemsDto) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderLineItemsDto = orderLineItemsDto;
    }

    public OrderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderLineItemsDto> getOrderLineItemsDto() {
        return orderLineItemsDto;
    }

    public void setOrderLineItemsDto(List<OrderLineItemsDto> orderLineItemsDto) {
        this.orderLineItemsDto = orderLineItemsDto;
    }
}
