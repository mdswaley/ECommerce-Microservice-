package com.example.orderservice.Controller;

import com.example.orderservice.DTO.OrderRequestDTO;
import com.example.orderservice.Service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<OrderRequestDTO>> getAllOrders(){
        List<OrderRequestDTO> orderRequestDTOS = ordersService.getAllOrders();
        return ResponseEntity.ok(orderRequestDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDTO> getOrdersById(@PathVariable Long id){
        OrderRequestDTO orderRequestDTOS = ordersService.getAllOrdersById(id);
        return ResponseEntity.ok(orderRequestDTOS);
    }

}
