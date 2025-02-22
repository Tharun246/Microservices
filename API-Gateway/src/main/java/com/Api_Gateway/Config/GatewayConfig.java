package com.Api_Gateway.Config;

import com.Api_Gateway.Service.MyUserDetailsService;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig
{
    @Autowired
    private MyUserDetailsService myUserDetailsService;


    private final BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/users").permitAll()
                        .anyExchange().authenticated())
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(myUserDetailsService);
        authenticationManager.setPasswordEncoder(new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B,12));

        return authenticationManager;
    }




//        @Bean
//        public MapReactiveUserDetailsService userDetailsService() {
//            UserDetails user = User.withDefaultPasswordEncoder()
//                    .username("Gojo")
//                    .password("Gojo@123")
//                    .roles("USER")
//                    .build();
//            return new MapReactiveUserDetailsService(user);
//        }
//    @Bean
//    public AuthenticationProvider getProvider()
//        {
//            DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//            provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//            provider.setUserDetailsService(myUserDetailsService);
//            return provider;
//        }






}
