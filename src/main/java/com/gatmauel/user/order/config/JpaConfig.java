package com.gatmauel.user.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//At least one JPA metamodel must be present!
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
