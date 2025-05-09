package com.example.orderservice.clients;

import com.example.orderservice.DTO.OrderRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryOpenFeignClient {
    @PutMapping("/products/reduce-stock")
    Double reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO);
}
