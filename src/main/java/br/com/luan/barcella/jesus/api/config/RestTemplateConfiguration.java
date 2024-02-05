package br.com.luan.barcella.jesus.api.config;

import static java.time.Duration.ofMillis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Value("${rest-template.timeout.connect}")
    private Integer connectTimeout;

    @Value("${rest-template.timeout.read}")
    private Integer readTimeout;

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(ofMillis(connectTimeout))
            .setReadTimeout(ofMillis(readTimeout))
            .build();
    }
}
