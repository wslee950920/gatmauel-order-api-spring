package com.gatmauel.user.order.domain.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OrderManagementTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderManagement orderManagement;

    @Test
    public void insert_order_null_failure(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            orderManagement.insertOrder(null);
        });
    }

    @Test
    public void insert_order_success(){
        String customer="맨유경비원";
        String phone="01020770883";
        int total=14000;
        String request="감사합니다.";
        String address="경기도 수원시";

        OrderDTO orderDTO=OrderDTO.builder()
                .customer(customer)
                .phone(phone)
                .total(total)
                .request(request)
                .address(address).build();

        Order order=Order.builder()
                .id(1L)
                .customer(customer)
                .total(total)
                .paid(0)
                .request(request)
                .address(address).build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Long orderId=orderManagement.insertOrder(orderDTO);

        verify(orderRepository).save(any(Order.class));

        assertThat(orderId).isEqualTo(order.getId());
    }
}
