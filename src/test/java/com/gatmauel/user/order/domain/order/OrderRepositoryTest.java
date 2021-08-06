package com.gatmauel.user.order.domain.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void insert_default_value_success(){
        Order order=Order.builder()
                .customer("맨유경비원")
                .phone("01020770883")
                .total(14000)
                .request("감사합니다:)")
                .address("수원시").build();

        Order newOrder=orderRepository.save(order);
        assertThat(newOrder.getPaid()).isEqualTo(0);
    }
}
