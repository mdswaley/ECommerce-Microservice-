package com.example.inventoryservice.Controller;

import com.example.inventoryservice.DTO.ProductDTO;
import com.example.inventoryservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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
