package com.gatmauel.user.order.domain.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class FoodDTO {
    private Long id;

    private String name;

    private Integer price;
}
