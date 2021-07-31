package com.gatmauel.user.order.domain.order;

import com.gatmauel.user.order.domain.detail.DetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class OrderDTO {
    private Long id;

    private String customer;

    private String phone;

    private Integer total;

    private Integer paid;

    private String request;

    private String address;

    private List<DetailDTO> details;
}
