## **Api Gateway**

A centralized entry point for managing and routing from clients to services **WebClient**.

### **pom.xml file**
   ```xml
   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   ```
### **yml file for routing and others**

1. **The below yml file configures the routes for 3 microservice and their database and jpa properties.**

 ```yaml
      server:
        port: 6757

      spring:
         main:
          web-application-type: reactive
   
      application:
       name: Gateway
   
      datasource:
         url: jdbc:postgresql://localhost:5432/Test
         username: postgres
         password: Global@477%
         driver-class-name: org.postgresql.Driver
   
      jpa:
      hibernate:
         ddl-auto: update       # Corrected syntax here (colon instead of equals and removed trailing colon)
         show-sql: true
         properties:
      properties:
        hibernate:
          dialect: org.hibernate.dialect.PostgreSQLDialect
   
      cloud:
         gateway:
            routes:
              - id: product-service
                uri: lb://prod-service
                predicates:
                    - Path=/api/products/**
   
              - id: order-service          # Changed 'Order-service' to 'order-service' (lowercase for consistency)
                uri: lb://order-service
                predicates:
                    - Path=/api/orders/**
   
              - id: inventory-service      # Changed 'Inventory-service' to 'inventory-service' (lowercase for consistency)
                uri: lb://inv-service
                predicates:
                    - Path=/api/inventory/**
   
      eureka :
        client:
          serviceUrl:
              defaultZone: http://localhost:8787/eureka/
   
      logging:
          level:
            org.springframework.boot.context.properties.bind: ERROR

 ```

---

## **Load Balancing**
**A mechanism to distribute the incoming client requests across multiple instance of a service**
```java
@Bean
    @LoadBalanced
    public WebClient.Builder webClient()
    {
            return WebClient.builder();
    }
```
**then call it**
```java
   webClientBuilder.build()
    .get()
    .uri("http://user-service/users")
    .retrieve()
    .bodyToMono(String.class);
```
**create a config package and create a bean like above and use it inside the other services**


## **Spring Cloud Config**
1. **Spring cloud config is a centralized configuration management system**
2. **It allows you store all app configuration into one place(typically a git repo)**

### Steps
1. Create config server 
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
    <version>4.3.0</version>
</dependency>
<dependencyManagement>
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>${spring-cloud.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
</dependencyManagement>
```
2. Annotate the main class with `@EnableConfigServer`
3. Create a `application.yml` file
```yaml
server:
  port: 8888
  
spring:
  application:
    name: config-repo
  cloud:
    config:
      server:
        git:
          uri: https://github.com/user-name/config-repo
          clone-on-start: true
```
4. Create a github repo ( same name maybe)
5. Now add `.yml` files with the same name as your services like
`user-service.yml`
```yaml
welcome.message:"Hello from user service"
```
 **Now this is a property you stored here and can refer it in your service**
6. Now in the `user-service` add the below
```xml
<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
```
7. Create a `bootstrap.yml` file
```yaml
spring:
  application:
    name: user-service
  cloud:
    config:
      uri: http://localhost:8888 #config server url
```
8. Now in the controller you can call
```java
@RestController
public class UserController{ 
   @Value("${welcome.message}")
   private String msg;
   
   @GetMapping("/name")
   private String sayHello()
   {
     return msg;
    }
}
```

