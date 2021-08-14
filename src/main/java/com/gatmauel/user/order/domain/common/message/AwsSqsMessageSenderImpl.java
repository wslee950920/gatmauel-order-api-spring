package com.gatmauel.user.order.domain.common.message;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsSqsMessageSenderImpl implements AwsSqsMessageSender {
    private final AwsSqsProperties awsSqsProperties;

    private final AmazonSQS amazonSQS;

    @Override
    public SendMessageResult sendMessage(String message){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(awsSqsProperties.getUrl(), message)
                .withMessageGroupId("gatmauel-order")
                .withMessageDeduplicationId(UUID.randomUUID().toString());;

        return amazonSQS.sendMessage(sendMessageRequest);
    }
}
