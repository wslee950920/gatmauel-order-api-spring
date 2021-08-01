package com.gatmauel.user.order.domain.detail;

import com.gatmauel.user.order.domain.food.Food;
import com.gatmauel.user.order.domain.order.Order;
import com.gatmauel.user.order.domain.order.OrderDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DetailManagementTest {
    @Mock
    private DetailRepository detailRepository;

    @InjectMocks
    private DetailManagement detailManagement;

    @Test
    public void insert_null_failure(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            detailManagement.insertDetails(null);
        });
    }

    @Test
    public void insert_empty_list_failure(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            String customer="맨유경비원";
            String phone="01020770883";
            int total=14000;
            String request="감사합니다.";
            String address="경기도 수원시";

            List<DetailDTO> detailDTOList=new ArrayList<>();

            OrderDTO orderDTO=OrderDTO.builder()
                    .customer(customer)
                    .phone(phone)
                    .total(total)
                    .request(request)
                    .address(address)
                    .details(detailDTOList).build();

            detailManagement.insertDetails(orderDTO);
        });
    }

    @Test
    public void insert_details_success(){
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

        Detail detail=Detail.builder()
                .id(1L)
                .food(Food.builder().id(1L).build())
                .order(Order.builder().id(1L).build())
                .num(2).build();

        when(detailRepository.save(any(Detail.class))).thenReturn(detail);

        detailManagement.insertDetails(orderDTO);

        verify(detailRepository).save(any(Detail.class));
    }
}
