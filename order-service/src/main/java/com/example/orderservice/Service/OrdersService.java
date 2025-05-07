package com.example.orderservice.Service;

import com.example.orderservice.DTO.OrderRequestDTO;
import com.example.orderservice.Entity.OrdersEntity;
import com.example.orderservice.Repository.OrdersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepo ordersRepo;
    private final ModelMapper modelMapper;

    public List<OrderRequestDTO> getAllOrders(){
        log.info("fetching all orders");
        List<OrdersEntity> ordersEntities = ordersRepo.findAll();

        return ordersEntities.stream()
                .map(ordersEntity -> modelMapper.map(ordersEntity, OrderRequestDTO.class))
                .collect(Collectors.toList());
    }

    public OrderRequestDTO getAllOrdersById(Long id){
        log.info("fetching order with id {}",id);
        Optional<OrdersEntity> ordersEntities = ordersRepo.findById(id);

        return ordersEntities.map(ordersEntity -> modelMapper.map(ordersEntity, OrderRequestDTO.class))
                .orElseThrow(()->new RuntimeException("order is not found"));
    }


}
