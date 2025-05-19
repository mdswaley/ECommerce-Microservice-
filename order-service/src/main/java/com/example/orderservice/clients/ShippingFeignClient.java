package com.example.orderservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shipping-service", path = "/shipping")
public interface ShippingFeignClient {

    @PostMapping("/ship-order/{order_id}")
    ResponseEntity<String> shipOrder(@PathVariable Long order_id);

    @GetMapping("/status/{order_id}")
    ResponseEntity<String> statusOfOrder(@PathVariable Long order_id);
}
