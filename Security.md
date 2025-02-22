## Spring Security
***Annotate the configuration file with `@EnableWebFluxSecurity`***
***
***Now Create a filter chain bean as below***
```java
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
```
***We need `ReactiveAuthenticationManager` to perform authentication***
```java
private final BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);
@Bean
public ReactiveAuthenticationManager authenticationManager() {
    UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
            new UserDetailsRepositoryReactiveAuthenticationManager(myUserDetailsService);
    authenticationManager.setPasswordEncoder(new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B,12));

    return authenticationManager;
}
```
### UserService
```
First register a user with a plain text password 
Now use Bcrypt encoder to store encrypted passwords
Change old user's password back to encrypted version
```
***Now while registering the user ,instead of storing plain text password we'll encrypt them as below***
```java
private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
public Users register(Users user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user);
    }
```
***Update the UserDetailService make it implement `ReactiveUserDetailsService`***
```java
@Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.justOrEmpty(repo.findByUserName(username))
                .map(UserPrincipal::new);
    }
```

***Create a new `JwtService` and `JwtFilter`(configuration) file***
```java
@Component
public class JwtFilter implements WebFilter
```














