package com.lucvs.apex_f1_api.infrastructure.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfig {

    @Bean
    fun couponIssueTopic(): NewTopic {
        return TopicBuilder.name("coupon-issue-topic")
            .partitions(3)
            .replicas(1)
            .build()
    }

    @Bean
    fun couponIssueDlqTopic(): NewTopic {
        return TopicBuilder.name("coupon-issue-dlq")
            .partitions(1)
            .replicas(1)
            .build()
    }
}