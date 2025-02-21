# `Spring Security Guide`
***Start with Adding below dependencies to `pom.xml` file***
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### `Create a config class`
```java
@Configuration
@EnableWebSecurity
public class securityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(request-> request.anyRequest().authenticated())
                .build();
    }
}
```
***

```
Now if you create a simple api for a get request and try to access it
You'll be asked to enter uname&pwd ( You can get those from console)
```

***You can add your own Cred in the`application.properties`***
```properties
spring.security.user.name=tharun
spring.security.user.password=abc@123
```
***Instead of hardcoding `userName and password`***
***We can use a `bean` to handle this***
`Create a Bean`
```java
@Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user1= User.withDefaultPasswordEncoder()
                .userName("Tharun")
                .password("T@123")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }
```
This will create a user and save it in the InMemory 
***Instead of using this manual process***
***We can use authentication provider***

## `Authentication provider`
```java
    @Bean
public AuthenticationProvider getProvider()
{
    DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
    provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    provider.setUserDetailsService(userService);
    return  provider;
}
```
`Users` ***Entity***
```java
@Entity
public class Users {
    @Id
    private Long id;

    private String userName;
    private String password;
}
```
`UserPrincipal` ***Entity***
```java
public class UserPrincipal implements UserDetails {
    private Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }
}
```
`MyUsersService`
```java
@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Users user= userRepo.findByUserName(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
```
`MyUserRepo` 
```java
@Repository
public interface MyUserRepo extends JpaRepository<Users,Long> {
    Users findByUserName(String username);
}
```
***Also create a controller and service method for the same***
`Use the below line in config to permit hitting some api without authentication`
```java
authorizeHttpRequests(request-> request
                        .requestMatchers("/users")
                        .permitAll()
                        .anyRequest()
                        .authenticated()) 
```