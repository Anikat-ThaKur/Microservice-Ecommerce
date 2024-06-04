package com.anikatelearning.orderservices.services;

import com.anikatelearning.orderservices.dto.InventoryResponse;
import com.anikatelearning.orderservices.dto.OrderContainerDto;
import com.anikatelearning.orderservices.dto.OrderRequest;
import com.anikatelearning.orderservices.event.OrderPlacedEvent;
import com.anikatelearning.orderservices.model.Order;
import com.anikatelearning.orderservices.model.OrderContainer;
import com.anikatelearning.orderservices.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientbuilder;
    public final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;


    public String placeOrder(OrderRequest orderRequest)
    {
        Order order =new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderContainer> orderContainer =orderRequest.getOrderContainerDtoList()
                .stream()
                .map(this::maptoDto)
                .toList();
        order.setOrderContainerList(orderContainer);

        List<String> skuCodes=order.getOrderContainerList().stream()
                .map(OrderContainer::getSkuCode)
                .toList();
        InventoryResponse[] Inventoryresponseresult = webClientbuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                         .block();
        boolean allProductsInStock = Arrays.stream(Inventoryresponseresult).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "Order Placed Successfully";
        }
        else {
            throw new IllegalArgumentException("Product is Out of Stock");
        }
    }
    private OrderContainer maptoDto(OrderContainerDto orderContainerDto)
    {
        OrderContainer orderContainer = new OrderContainer();
        orderContainer.setPrice(orderContainerDto.getPrice());
        orderContainer.setQuantity(orderContainerDto.getQuantity());
        orderContainer.setSkuCode(orderContainerDto.getSkuCode());
        return orderContainer;
    }
}
