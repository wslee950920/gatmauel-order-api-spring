package com.gatmauel.user.order.domain.detail;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class DetailDTO {
    private Long foodId;

    private String foodName;

    private Integer num;
}
