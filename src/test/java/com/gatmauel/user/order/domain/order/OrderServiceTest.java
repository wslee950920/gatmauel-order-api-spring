package com.gatmauel.user.order.domain.order;

import com.gatmauel.user.order.domain.detail.DetailDTO;
import com.gatmauel.user.order.domain.detail.DetailManagement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
public class OrderServiceTest {
    @Mock   //Mock을 주입하면 OrderService내부의 OrderManagement를 대체한다.
    private OrderManagement orderManagement;

    @Mock
    private DetailManagement detailManagement;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp(){
        //Mock은 스프링 context에 올라가지 않기 때문에 직접 주입해주어야 한다.
        this.orderService=new OrderServiceImpl(orderManagement, detailManagement);
    }

    @Test
    public void make_order_null_failure(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            orderService.makeOrder(null);
        });
    }

    @Test
    public void make_order_success(){
        String customer="맨유경비원";
        String phone="01020770883";
        int total=14000;
        String request="감사합니다.";
        String address="경기도 수원시";

        DetailDTO detailDTO=DetailDTO.builder()
                .foodId(1L)
                .foodName("칼국수")
                .num(2).build();

        List<DetailDTO> detailDTOList=new ArrayList<>();
        detailDTOList.add(detailDTO);

        OrderDTO orderDTO=OrderDTO.builder()
                .customer(customer)
                .phone(phone)
                .total(total)
                .request(request)
                .address(address)
                .details(detailDTOList).build();

        Long orderId=1L;
        when(orderManagement.insertOrder(orderDTO)).thenReturn(orderId);

        orderDTO=orderService.makeOrder(orderDTO);

        verify(orderManagement).insertOrder(orderDTO);
        verify(detailManagement).insertDetails(orderDTO);

        assertThat(orderDTO.getId()).isEqualTo(orderId);
        assertThat(orderDTO.getCustomer()).isEqualTo(customer);
        assertThat(orderDTO.getPhone()).isEqualTo(phone);
        assertThat(orderDTO.getTotal()).isEqualTo(total);
        assertThat(orderDTO.getRequest()).isEqualTo(request);
        assertThat(orderDTO.getAddress()).isEqualTo(address);
        for(DetailDTO d:orderDTO.getDetails()){
            assertThat(d.getFoodId()).isEqualTo(detailDTO.getFoodId());
            assertThat(d.getFoodName()).isEqualTo(detailDTO.getFoodName());
            assertThat(d.getNum()).isEqualTo(detailDTO.getNum());
        }
    }
}
