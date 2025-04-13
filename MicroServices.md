# **Microservices**

## **Inter-Service Communication**

To facilitate communication between services, we will use **WebClient**.

### **Steps to Set Up WebClient**

1. **Add the Spring WebFlux dependency** to your project to enable WebClient.
2. **Create a configuration file** in the order service with a bean to return the WebClient instance, as shown below:
3. When we send a request from order service it'll go to inventory service
   But as we have multiple instances of inventory running , we need to enable load balancing

    ```java
    @Bean
   @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder().build();
    }
    ```

### **Modifying the Order Service**

In the order service, when a new order is placed, we will first call the **Inventory Service** to check if the product is available. If available, the order will proceed.

### **Passing Query Parameters Using WebClient**

To pass query parameters using WebClient, you can make a **GET request** like this:

``` java
InvResponse[] invResponses = webClient.Builder.build()
        .get()
        .uri("http://inventory-service/api/inventory",
             uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
        .retrieve()
        .bodyToMono(InvResponse[].class)
        .block();
```

---

## **Service Discovery**

### **Eureka Server**

To enable service discovery, we will create a discovery service that stores information about service names and their respective ports.

### **Steps to Set Up Eureka Server**

1. **Add the following dependency** to your `pom.xml`:

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        <version>3.1.1</version>
    </dependency>
    ```
**Add the below in Parent pom.xml file**
```xml
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
```xml
 <properties>
    <java.version>17</java.version>
    <spring-cloud.version>2024.0.0</spring-cloud.version>
 </properties>
```
2. **Create a Spring Boot application file** and annotate it with `@EnableEurekaServer`:

    ```java
    @EnableEurekaServer
    @SpringBootApplication
    public class EurekaServerApplication {
        public static void main(String[] args) {
            SpringApplication.run(EurekaServerApplication.class, args);
        }
    }
    ```

3.***In your `application.properties`, configure Eureka Server***:

    
    eureka.instance.hostname=localhost
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    server.port=8761
    

### **Inside each service's `pom.xml`**

Add the following dependency:

``` xml
 <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
```

Then, annotate each microservice with `@EnableDiscoveryClient`:

   ``` java
@EnableDiscoveryClient
   ```
### **In `application.properties` for Each Service**

Add the following configuration:
   ``` properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
eureka.instance.prefer-ip-address=true
   ```
---

## **Dynamic Port**

Instead of hardcoding the port, you can set the port as **0** to let Spring Boot select an available port during runtime.
Add below to the properties file
```properties
server.port=0
eureka.instance.instance-id=${spring.application.name}:${random.int}
```

## **API Gateway**
> Route requests from user to the respective service
### Add the below dependencies
```xml
 <dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```
```xml
 <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
```
### `Application.properties` file
```properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
spring.application.name=api-gateway
eureka.instance.prefer-ip-address=true
```
**For Each MicroService Add the below**
 ```properties
# Product service route
spring.cloud.gateway.routes[0].id=product-service
spring.cloud.gateway.routes[0].uri=lb://product-service
spring.cloud.gateway.routes[0].predicates[0]=Path=/api/products # should be same as the endpoint 
```

**Accessing**
> Now if you make a request `http://localhost:8080/api/products` it'll redirect to `/api/products`

***We can also make the request to discovery server go through gateway***
```properties
# Discovery service route
spring.cloud.gateway.routes[3].id=eureka-server
spring.cloud.gateway.routes[3].uri=lb://localhost:8761
spring.cloud.gateway.routes[3].predicates[0]=Path=/eureka/web
```
***Now the issue is we don't have an api like this configured in discovery server*** `/eureka/web`

```
We want to hit /eureka/hub and want it to to redirect us to
localhost:8761/
```
***To achieve the above we need to add the below filter***
```properties
spring.cloud.gateway.routes[3].filters[0]=SetPath=/
```
```properties
# Discovery service Static resource route
spring.cloud.gateway.routes[4].id=eureka-server
spring.cloud.gateway.routes[4].uri=http://localhost:8761
spring.cloud.gateway.routes[4].predicates[0]=Path=/eureka/**
```







