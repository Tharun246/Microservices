    package com.Api_Gateway.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class GatewayConfig
{
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(Customizer.withDefaults())
                    .authorizeHttpRequests(request->request.anyRequest().authenticated())
                    .build();
        }
        @Bean
        public InMemoryUserDetailsManager getService()
        {
            UserDetails user1= User.withDefaultPasswordEncoder()
                    .username("Gojo")
                    .password("Gojo@123")
                    .build();

            return new InMemoryUserDetailsManager(user1);

        }
    }
