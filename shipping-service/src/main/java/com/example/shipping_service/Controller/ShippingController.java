package com.example.shipping_service.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

    @PostMapping("/ship-order/{order_id}")
    public ResponseEntity<String> shipOrder(@PathVariable Long order_id){
        return ResponseEntity.ok("order is shipping successfully with id : "+order_id);
    }

    @GetMapping("/status/{order_id}")
    public ResponseEntity<String> statusOfOrder(@PathVariable Long order_id){
        return ResponseEntity.ok("Shipped");
    }

}
