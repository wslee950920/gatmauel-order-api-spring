package com.gatmauel.user.order.web.apis;

import com.gatmauel.user.order.domain.detail.DetailDTO;
import com.gatmauel.user.order.domain.order.OrderDTO;
import com.gatmauel.user.order.domain.order.OrderService;
import com.gatmauel.user.order.utils.JsonUtils;
import com.gatmauel.user.order.web.payload.request.MakeOrderRequestPayload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderApiControllerTest {
    @MockBean   //OrderService MockBean을 스프링 context에 올린다. OrderService빈이 존재하면 OrderService빈을 대체한다.
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void make_order_invalid_request_payload_return_400() throws Exception{
        MakeOrderRequestPayload requestPayload=MakeOrderRequestPayload.builder().build();

        mvc.perform(
                post("/@user/order/make")
                        .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                        .content(JsonUtils.toJson(requestPayload)))
                .andExpect(status().is(400));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void make_order_valid_request_payload_return_200() throws Exception{
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

        MakeOrderRequestPayload requestPayload=MakeOrderRequestPayload.builder()
                .customer(customer)
                .phone(phone)
                .total(total)
                .request(request)
                .address(address)
                .details(detailDTOList).build();

        OrderDTO orderDTO=requestPayload.toDTO();
        when(orderService.makeOrder(requestPayload.toDTO())).thenReturn(orderDTO);

        mvc.perform(
                post("/@user/order/make")
                    .contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                    .content(JsonUtils.toJson(requestPayload)))
                .andExpect(status().is(200));
    }
}
