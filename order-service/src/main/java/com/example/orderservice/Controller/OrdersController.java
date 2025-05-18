package com.example.orderservice.Controller;

import com.example.orderservice.DTO.OrderRequestDTO;
import com.example.orderservice.DTO.OrderRequestItemDTO;
import com.example.orderservice.Service.OrdersService;
import com.example.orderservice.clients.InventoryOpenFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;


    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        OrderRequestDTO orderRequestDTO1 = ordersService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(orderRequestDTO1);
    }

    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "hello from orders service";
    }

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

    @PutMapping("/cancel/{orderId}")
    ResponseEntity<String> cancelOrderInOrderService(@PathVariable("orderId") Long orderId){
        boolean isCancel = ordersService.cancelOrder(orderId);

        if (isCancel) {
            return ResponseEntity.ok("Order cancelled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Order already cancelled or not found.");
        }
    }

}
