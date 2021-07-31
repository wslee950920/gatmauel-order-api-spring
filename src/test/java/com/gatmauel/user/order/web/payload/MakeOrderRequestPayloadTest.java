package com.gatmauel.user.order.web.payload;

import com.gatmauel.user.order.web.payload.request.MakeOrderRequestPayload;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest  //@SpringBootTest를 쓰면 @Service 등 모두 불러온다.
public class MakeOrderRequestPayloadTest {
    @Autowired
    private Validator validator;

    @Test
    public void validate_blank_dto_fail(){
        MakeOrderRequestPayload requestPayload = MakeOrderRequestPayload.builder().build();

        Set<ConstraintViolation<MakeOrderRequestPayload>> violations=validator.validate(requestPayload);
        for(ConstraintViolation<MakeOrderRequestPayload> violation:violations){
            log.debug(violation.getMessage());
        }
        assertThat(violations.size()).isEqualTo(6);
    }

    @Test
    public void validate_invalid_dto_fail(){
        String blankCustomer="";
        String shortPhone="0102077088";
        int notPositive=0;
        String fineBlankRequest="";
        String onlyWhiteSpaceAddress=" ";
        List emptyList=new ArrayList<>();

        MakeOrderRequestPayload requestPayload = MakeOrderRequestPayload.builder()
                .customer(blankCustomer)
                .phone(shortPhone)
                .total(notPositive)
                .request(fineBlankRequest)
                .address(onlyWhiteSpaceAddress)
                .details(emptyList)
                .build();

        Set<ConstraintViolation<MakeOrderRequestPayload>> violations=validator.validate(requestPayload);
        for(ConstraintViolation<MakeOrderRequestPayload> violation:violations){
            log.debug(violation.getMessage());
        }
        assertThat(violations.size()).isEqualTo(5);
    }
}
