package io.alfredux.spring.audit.auditablecrud.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password")
                .roles("USER", "ADMIN")
                .and()
                .withUser("admin")
                .password("{noop}password")
                .roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/").permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll();

        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/customers/**").hasRole("USER")
                .antMatchers(POST, "/customers").hasRole("ADMIN")
                .antMatchers(PUT, "/customers/**").hasRole("ADMIN")
                .antMatchers(PATCH, "/customers/**").hasRole("ADMIN")
                .antMatchers(DELETE, "/customers/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();

        http.csrf().disable();
        http.headers().frameOptions().disable();

    }
}
