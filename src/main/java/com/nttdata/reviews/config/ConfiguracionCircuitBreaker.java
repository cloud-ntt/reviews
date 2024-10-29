package com.nttdata.reviews.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Function;

@Configuration
public class ConfiguracionCircuitBreaker {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id).circuitBreakerConfig(
                    CircuitBreakerConfig.custom()
                        .slidingWindowSize(20)//nro de request permitidos para hacer el control de circuitB. default 100
                        .failureRateThreshold(50L)//% de request erroneos para abrir el circuito. default 50%
                        .waitDurationInOpenState(Duration.ofSeconds(20))//tiempo de espera en el estado abierto para cambiar a semiAbierto. default 60s
                        .permittedNumberOfCallsInHalfOpenState(5)//nro de request en estado semiA. default 10
                        .build())
                    .timeLimiterConfig(TimeLimiterConfig.ofDefaults()).build();
        });
    }

}
