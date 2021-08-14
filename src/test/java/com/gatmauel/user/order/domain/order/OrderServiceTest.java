package com.gatmauel.user.order.domain.order;

import com.gatmauel.user.order.domain.common.message.AwsSqsMessageSender;
import com.gatmauel.user.order.domain.detail.DetailDTO;
import com.gatmauel.user.order.domain.detail.DetailManagement;
import com.gatmauel.user.order.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Slf4j
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderManagement orderManagement;

    @Mock
    private DetailManagement detailManagement;

    @Mock
    private AwsSqsMessageSender awsSqsMessageSender;

    @InjectMocks
    private OrderServiceImpl orderService;

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
        verify(awsSqsMessageSender).sendMessage(JsonUtils.toJson(orderDTO));

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
