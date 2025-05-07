package com.example.inventoryservice.Controller;

import com.example.inventoryservice.DTO.ProductDTO;
import com.example.inventoryservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders(){
        log.info("before order");
        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service")
                .stream().findFirst().orElse(null);
        return restClient.get()
                .uri(serviceInstance.getUri()+"/orders/core/helloOrders")
                .retrieve()
                .body(String.class);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory(){
        List<ProductDTO> inventory = productService.getAllInventory();
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getAllInventoryById(@PathVariable Long id){
        ProductDTO inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }


}
