package org.gfg.TransactionServiceApplication.configuration;

import org.gfg.TransactionServiceApplication.service.TxnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Autowired
    private TxnService txnService;

    @Autowired
    private CommonConfig commonConfig;

    @Value("${user.Authority}")
    private String userAuthority;

    @Value("${admin.Authority}")
    private String adminAuthority;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(txnService);
        authenticationProvider.setPasswordEncoder(commonConfig.getEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/txn/initTxn/**").hasAuthority(userAuthority)
//                        .requestMatchers("/user/addAdmin/**").permitAll()
//                        .requestMatchers("/user/filter/**").hasAnyAuthority(adminAuthority, studentAuthority)
//                        .requestMatchers("/txn/create/**").hasAuthority(adminAuthority)
//                        .requestMatchers("/txn/return/**").hasAuthority(adminAuthority)
//                        .requestMatchers("/book/addBook/**").hasAuthority(adminAuthority)
//                        .requestMatchers("/book/filter/**").hasAnyAuthority(studentAuthority, adminAuthority)
                                .anyRequest().permitAll()
                ).formLogin(withDefaults()).httpBasic(withDefaults()).csrf(csrf -> csrf.disable());
        return http.build();
    }


}
