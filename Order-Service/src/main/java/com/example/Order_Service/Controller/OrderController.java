package com.example.Order_Service.Controller;

import com.example.Order_Service.Dto.OrderRequest;


import com.example.Order_Service.Dto.OrderResponse;
import com.example.Order_Service.Model.Order;
import com.example.Order_Service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void placeOrder(@RequestBody OrderRequest orderRequest)
    {
        System.out.println(orderRequest.getOrderLineItemsDto());
        orderService.placeOrder(orderRequest);
    }

    @GetMapping
    public List<Order> getOrders()
    {
        return orderService.getOrders();
    }

//    @GetMapping
//    public List<OrderResponse> getOrderResponse()
//    {
//        return orderService.getOrderResponse();
//    }
}
