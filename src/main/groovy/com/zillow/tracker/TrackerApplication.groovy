package com.zillow.tracker

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.cassandra.core.CassandraOperations

@SpringBootApplication
class TrackerApplication {

    static void main(String[] args) {
        SpringApplication.run(TrackerApplication, args)
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }
}
