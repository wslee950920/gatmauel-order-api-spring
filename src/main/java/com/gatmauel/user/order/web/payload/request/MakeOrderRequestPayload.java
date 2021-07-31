package com.gatmauel.user.order.web.payload.request;

import com.gatmauel.user.order.domain.detail.DetailDTO;

import com.gatmauel.user.order.domain.order.OrderDTO;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor //@Builder를 클래스 단위로 적용하려면 필요
public class MakeOrderRequestPayload {
    @NotBlank   //중간에 공백은 상관없는듯
    private String customer;

    @NotNull
    @Size(min = 11, max = 11)
    private String phone;

    @NotNull
    @Positive
    private Integer total;

    @NotNull
    private String request;

    @NotBlank
    @Size(max = 100)
    private String address;

    @NotEmpty
    private List<DetailDTO> details;

    public OrderDTO toDTO(){
        OrderDTO orderDTO=OrderDTO.builder()
                .customer(this.customer)
                .phone(this.phone)
                .total(this.total)
                .request(this.request)
                .address(this.address)
                .details(this.details).build();

        return orderDTO;
    }
}
