package com.gatmauel.user.order.domain.detail;

import com.gatmauel.user.order.domain.food.Food;
import com.gatmauel.user.order.domain.order.Order;
import com.gatmauel.user.order.domain.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Component
public class DetailManagement {
    private final DetailRepository detailRepository;

    public void insertDetails(OrderDTO orderDTO){
        Assert.notNull(orderDTO, "orderDTO must not null");
        Assert.notEmpty(orderDTO.getDetails(), "list must not empty");

        for(DetailDTO d: orderDTO.getDetails()){
            Detail detail=Detail.builder()
                    .food(Food.builder().id(d.getFoodId()).build())
                    .order(Order.builder().id(orderDTO.getId()).build())
                    .num(d.getNum()).build();
            detailRepository.save(detail);
        }
    }
}
