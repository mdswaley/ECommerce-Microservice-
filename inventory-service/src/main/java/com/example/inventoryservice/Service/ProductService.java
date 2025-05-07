package com.example.inventoryservice.Service;

import com.example.inventoryservice.DTO.ProductDTO;
import com.example.inventoryservice.Entity.ProductEntity;
import com.example.inventoryservice.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;

    public List<ProductDTO> getAllInventory(){
        log.info("fetching all inventory items.");
        List<ProductEntity> inventory = productRepo.findAll();

        return inventory.stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductDTO.class))
                .toList();
    }

    public ProductDTO getProductById(Long id){
        log.info("fetching product with id : {}",id);
        Optional<ProductEntity> inventory = productRepo.findById(id);
        return inventory.map(item->modelMapper.map(item,ProductDTO.class))
                .orElseThrow(()->new RuntimeException("Inventory not found"));
    }
}
