package com.example.inventoryservice.clients;

import com.example.inventoryservice.DTO.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="order-service", path = "/orders")
public interface OrderFeignClients {

    @GetMapping("/core/{order_id}")
    OrderRequestDto getOrdersById(@PathVariable Long order_id);

    @PutMapping("/core/cancel/{orderId}")
    String cancelOrderInOrderService(@PathVariable("orderId") Long orderId);

    @GetMapping("/core/helloOrders")
    String helloOrders();
}
