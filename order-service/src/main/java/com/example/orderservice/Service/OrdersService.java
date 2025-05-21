package com.example.orderservice.Service;

import com.example.orderservice.DTO.OrderRequestDTO;
import com.example.orderservice.Entity.OrderItemsEntity;
import com.example.orderservice.Entity.OrdersEntity;
import com.example.orderservice.Entity.OrdersStatus;
import com.example.orderservice.Repository.OrdersRepo;
import com.example.orderservice.clients.InventoryOpenFeignClient;
import com.example.orderservice.clients.ShippingFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.orderservice.Entity.OrdersStatus.CANCELLED;
import static com.example.orderservice.Entity.OrdersStatus.PLACED;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepo ordersRepo;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

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


//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Calling the create order method.");
        double total = inventoryOpenFeignClient.reduceStocks(orderRequestDTO);

        OrdersEntity ordersEntity = modelMapper.map(orderRequestDTO,OrdersEntity.class);
        for (OrderItemsEntity orderItemsEntity:ordersEntity.getItems()){
            orderItemsEntity.setOrders(ordersEntity);
        }

        ordersEntity.setTotal(total);
        ordersEntity.setOrdersStatus(OrdersStatus.CONFIRM);

        return modelMapper.map(ordersRepo.save(ordersEntity), OrderRequestDTO.class);
    }

    public OrderRequestDTO createOrderFallback(OrderRequestDTO orderRequestDTO, Throwable throwable) {
        log.error("fallback occurred due to : {}", throwable.getMessage());
        return new OrderRequestDTO();
    }

    public boolean cancelOrder(Long orderId) {
        OrdersEntity ordersEntity = ordersRepo.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order with id is not found :"+orderId));

        if (ordersEntity.getOrdersStatus() == CANCELLED) {
            return false;
        }

        ordersEntity.setOrdersStatus(CANCELLED);
        ordersRepo.save(ordersEntity);
        return true;
    }

    @Retry(name = "shippingOrderRetry", fallbackMethod = "placeOrderFallback")
    public String placeOrder(Long orderId) {
        OrdersEntity order = ordersRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        if (order.getOrdersStatus() == PLACED) {
            throw new RuntimeException("Order is already placed.");
        }

        order.setOrdersStatus(PLACED);
        ordersRepo.save(order);

        return "Order placed successfully with id : "+orderId;
    }

    public void placeOrderFallback(Throwable throwable){
        log.error("fallback occurred due to some issue : {}", throwable.getMessage());
    }
}
