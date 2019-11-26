package com.zillow.tracker

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@PropertySources([
        @PropertySource(value = 'classpath:templates.properties'),
])
@CompileStatic
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
