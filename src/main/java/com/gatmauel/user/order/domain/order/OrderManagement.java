package com.gatmauel.user.order.domain.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderManagement {
    private final OrderRepository orderRepository;

    public Long insertOrder(OrderDTO orderDTO){
        Assert.notNull(orderDTO, "orderDTO must not null");

        Order order=Order.builder()
                .customer(orderDTO.getCustomer())
                .phone(orderDTO.getPhone())
                .total(orderDTO.getTotal())
                .request(orderDTO.getRequest())
                .address(orderDTO.getAddress()).build();
        order=orderRepository.save(order);

        return order.getId();
    }
}
