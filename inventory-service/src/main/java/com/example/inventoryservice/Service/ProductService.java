package com.example.inventoryservice.Service;

import com.example.inventoryservice.DTO.OrderRequestDto;
import com.example.inventoryservice.DTO.OrderRequestItemDto;
import com.example.inventoryservice.DTO.ProductDTO;
import com.example.inventoryservice.Entity.ProductEntity;
import com.example.inventoryservice.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Double reduceStock(OrderRequestDto orderRequestDto) {
        log.info("Reducing product.");
        double total = 0.0;

        for(OrderRequestItemDto orderRequestItemDto:orderRequestDto.getItems()){
            Long proId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            ProductEntity productEntity = productRepo.findById(proId).orElseThrow(
                    ()->new RuntimeException("Product is not found with id "+proId));

            if(productEntity.getStock() < quantity){
                throw new RuntimeException("product can not be fulfilled with given quantity.");
            }

            productEntity.setStock(productEntity.getStock() - quantity);
            productRepo.save(productEntity);
            total += productEntity.getPrice() * quantity;
        }

        return total;
    }
}
