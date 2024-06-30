package org.gfg.TransactionServiceApplication.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfig {

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
