package com.example.Order_Service.Service;

import com.example.Order_Service.Dto.InvResponse;
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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

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

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> list = orderRequest.getOrderLineItemsDto()
                .stream()
                .map(this::mapToOrder)
                .toList();

        order.setOrderLineItemsList(list);

        List<String> skuCodes=order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        // Pass the skuCode list
        InvResponse[] res = webClientBuilder.build()
                .get().
                uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InvResponse[].class)
                .block();


        // check if every item from the list available
        boolean isIn= Arrays.stream(res).allMatch(InvResponse::isInStock);

        if (res.length>0 && isIn)
        {
            orderRepository.save(order);
            System.out.println("Order Placed Successfully");
            return "Order Placed";
        }
        else {
            return("Product not in stock");
        }
    }

    public List<Order> getOrders()
    {
        return orderRepository.findAll();
    }

}
