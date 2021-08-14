package com.gatmauel.user.order.domain.common.message;

import com.amazonaws.services.sqs.model.SendMessageResult;

public interface AwsSqsMessageSender {
    SendMessageResult sendMessage(String message);
}
