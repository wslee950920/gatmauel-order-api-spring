package com.gatmauel.user.order.domain.order;

import com.gatmauel.user.order.domain.common.message.AwsSqsMessageSender;
import com.gatmauel.user.order.domain.detail.DetailManagement;
import com.gatmauel.user.order.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderManagement orderManagement;

    private final DetailManagement detailManagement;

    private final AwsSqsMessageSender awsSqsMessageSender;

    @Override
    @Transactional
    public OrderDTO makeOrder(OrderDTO orderDTO){
        Assert.notNull(orderDTO, "orderDTO must not null");

        Long orderId=orderManagement.insertOrder(orderDTO);
        orderDTO.setId(orderId);

        detailManagement.insertDetails(orderDTO);

        awsSqsMessageSender.sendMessage(JsonUtils.toJson(orderDTO));

        return orderDTO;
    }
}
