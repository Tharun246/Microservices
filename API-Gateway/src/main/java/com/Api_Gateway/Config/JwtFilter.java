package com.Api_Gateway.Config;

import com.Api_Gateway.Model.UserPrincipal;
import com.Api_Gateway.Service.JwtService;
import com.Api_Gateway.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter
{
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;
    @Override

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        Mono<UserDetails> userDetailsMono =
                Mono.justOrEmpty(userDetailsService.findByUsername(jwtService.getUname(token)))
                        .flatMap(user -> {
                            if (user instanceof UserDetails) {
                                return Mono.just((UserDetails) user);
                            } else {
                                // Handle or throw an exception for unexpected type.
                                throw new RuntimeException("Unexpected UserDetails type");
                            }
                        });

        return userDetailsMono.flatMap(userDetails -> {
            if (!jwtService.validateToken(token, userDetails)) {
                return chain.filter(exchange); // Or handle invalid token scenario appropriately.
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());

            SecurityContextHolder.setContext(new SecurityContextImpl(authentication));

            ServerHttpRequest modifiedRequest =
                    new ServerHttpRequestDecorator(request) {
                        @Override
                        public HttpHeaders getHeaders() {
                            HttpHeaders headers = super.getHeaders();
                            headers.remove(HttpHeaders.AUTHORIZATION);
                            return headers; // Optionally remove auth header after validation.
                        }
                    };

            ServerWebExchange modifiedExchange =
                    exchange.mutate().request(modifiedRequest).build();

            return chain.filter(modifiedExchange);

// Handle errors appropriately here...
        }).onErrorResume(e -> chain.filter(exchange)); // Handle exceptions gracefully.
}
}
