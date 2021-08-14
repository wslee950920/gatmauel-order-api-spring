package com.gatmauel.user.order.domain.detail;

import com.gatmauel.user.order.domain.food.Food;
import com.gatmauel.user.order.domain.order.Order;
import com.gatmauel.user.order.domain.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@RequiredArgsConstructor
@Component
public class DetailManagement {
    private final DetailRepository detailRepository;

    public void insertDetails(OrderDTO orderDTO){
        Assert.notNull(orderDTO, "orderDTO must not null");
        Assert.notEmpty(orderDTO.getDetails(), "list must not empty");

        for(DetailDTO d: orderDTO.getDetails()){
            log.debug("food id : "+d.getFoodId());
            Food food=Food.builder().id(d.getFoodId()).build();

            Detail detail=Detail.builder()
                    .food(food)
                    .order(Order.builder().id(orderDTO.getId()).build())
                    .num(d.getNum()).build();
            detailRepository.save(detail);
        }
    }
}
