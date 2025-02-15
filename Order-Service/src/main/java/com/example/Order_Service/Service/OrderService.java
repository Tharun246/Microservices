package com.example.Order_Service.Service;

import com.example.Order_Service.Dto.OrderLineItemsDto;
import com.example.Order_Service.Dto.OrderRequest;
import com.example.Order_Service.Dto.OrderResponse;
import com.example.Order_Service.Model.Order;
import com.example.Order_Service.Model.OrderLineItems;
import com.example.Order_Service.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    //    @Autowired
    private WebClient webClient;

    public OrderLineItems mapToOrder(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }

    public OrderLineItemsDto mapToDto(OrderLineItems orderLineItems) {
        OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
        orderLineItemsDto.setPrice(orderLineItems.getPrice());
        orderLineItemsDto.setSkuCode(orderLineItems.getSkuCode());
        orderLineItemsDto.setQuantity(orderLineItems.getQuantity());

        return orderLineItemsDto;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setId(order.getId());

        orderResponse.setOrderNumber(order.getOrderNumber());

        List<OrderLineItemsDto> list = order.getOrderLineItemsList()
                .stream()
                .map(this::mapToDto)
                .toList();

        orderResponse.setOrderLineItemsDto(list);

        return orderResponse;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> list = orderRequest.getOrderLineItemsDto()
                .stream()
                .map(this::mapToOrder)
                .toList();

        order.setOrderLineItemsList(list);
        // Call inventory service if it is in stock
        Boolean res = webClient.get().uri("http://localhost:8890/api/inventory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.TRUE.equals(res))
            orderRepository.save(order);
        throw new IllegalArgumentException("Product not in stock");
    }

    public List<Order> getOrders()
    {
        return orderRepository.findAll();
    }

}
