package com.gatmauel.user.order.web.apis;

import com.gatmauel.user.order.domain.order.OrderDTO;
import com.gatmauel.user.order.domain.order.OrderService;
import com.gatmauel.user.order.web.payload.request.MakeOrderRequestPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/@user/order")
@RestController
public class OrderApiController {
    private final OrderService orderService;

    @PostMapping("/make")
    public ResponseEntity<Map<String, Object>> makeOrder(@Valid @RequestBody MakeOrderRequestPayload requestPayload){
        OrderDTO orderDTO=orderService.makeOrder(requestPayload.toDTO());

        Map<String, Object> response=new HashMap<>();
        response.put("id", orderDTO.getId());
        response.put("customer", orderDTO.getCustomer());
        response.put("total", orderDTO.getTotal());
        response.put("request", orderDTO.getRequest());
        response.put("address", orderDTO.getAddress());
        response.put("details", orderDTO.getDetails());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
