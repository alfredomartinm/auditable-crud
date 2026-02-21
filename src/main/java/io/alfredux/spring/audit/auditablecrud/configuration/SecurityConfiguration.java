package io.alfredux.spring.audit.auditablecrud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService users() {
    UserDetails user = User.withUsername("user")
        .password("{noop}password")
        .roles("USER", "ADMIN")
        .build();

    UserDetails admin = User.withUsername("admin")
        .password("{noop}password")
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/", "/h2-console/**").permitAll()
            .requestMatchers(GET, "/customers/**").hasRole("USER")
            .requestMatchers(POST, "/customers").hasRole("ADMIN")
            .requestMatchers(PUT, "/customers/**").hasRole("ADMIN")
            .requestMatchers(PATCH, "/customers/**").hasRole("ADMIN")
            .requestMatchers(DELETE, "/customers/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .httpBasic(httpBasic -> {
        })
        .formLogin(formLogin -> formLogin.disable())
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

    return http.build();
    }
}
