package com.example.Order_Service.Dto;
import java.util.List;

public class OrderRequest {

    private List<OrderLineItemsDto> orderLineItemsDto;

    public OrderRequest(List<OrderLineItemsDto> orderLineItemsDto) {
        this.orderLineItemsDto = orderLineItemsDto;
    }

    public OrderRequest() {
    }

    public List<OrderLineItemsDto> getOrderLineItemsDto() {
        return orderLineItemsDto;
    }

    public void setOrderLineItemsDto(List<OrderLineItemsDto> orderLineItemsDto) {
        this.orderLineItemsDto = orderLineItemsDto;
    }
}

