package com.gatmauel.user.order.domain.common.error;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiError {
    private List<String> messages;

    public ApiError(List<String> messages) {
        super();
        this.messages = messages;
    }

    public ApiError(String message) {
        super();
        this.messages = Arrays.asList(message);
    }
}
