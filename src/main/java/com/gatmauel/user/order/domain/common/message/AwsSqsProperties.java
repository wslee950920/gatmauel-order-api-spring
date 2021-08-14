package com.gatmauel.user.order.domain.common.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws.sqs.queue")
@Component
public class AwsSqsProperties {
    private String url;
}
