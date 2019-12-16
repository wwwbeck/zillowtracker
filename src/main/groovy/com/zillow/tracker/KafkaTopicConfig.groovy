package com.zillow.tracker

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig {

    @Value('${kafka.bootstrapAddress}')
    String kafkaBootstrapAddress

    /**
     * create beans programmatically
     * @return
     */
    @Bean
    KafkaAdmin kafkaAdmin() {
        Map<String, String> configs = [
                (AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG): kafkaBootstrapAddress
        ]
        return new KafkaAdmin(configs)
    }

    @Bean
    NewTopic topic() {
        return new NewTopic('zillow-tracker', 1, (short) 1)
    }
}
