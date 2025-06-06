# **Api Gateway**

A centralized entry point for managing and routing from clients to services **WebClient**.

### **pom.xml file**
   ```xml
   <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
   </dependency>
   ```
### **yml file for routing and others**

1. **The below yml file configures the routes for 3 microservice and their database and jpa properties.

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

### **Load Balancing**
**A mechanism to distribute the incoming client requests across multiple instance of a service**



