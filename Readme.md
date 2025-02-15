# **Microservices**

## **Inter-Service Communication**

To facilitate communication between services, we will use **WebClient**.

### **Steps to Set Up WebClient**

1. **Add the Spring WebFlux dependency** to your project to enable WebClient.
2. **Create a configuration file** in the order service with a bean to return the WebClient instance, as shown below:

    ```java
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
    ```

### **Modifying the Order Service**

In the order service, when a new order is placed, we will first call the **Inventory Service** to check if the product is available. If available, the order will proceed.

### **Passing Query Parameters Using WebClient**

To pass query parameters using WebClient, you can make a **GET request** like this:

``` java
InvResponse[] invResponses = webClient.get()
        .uri("http://localhost:8081/api/inventory",
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

3. **In your `application.properties`, configure Eureka Server**:

    ```properties
    eureka.instance.hostname=localhost
    eureka.client.register-with-eureka=false
    eureka.client.fetch-registry=false
    server.port=8761
    ```

### **Inside each service's `pom.xml`**

Add the following dependency:

``` xml
 <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
```

Then, annotate each microservice with `@EnableEurekaClient`:

   ``` java
@EnableEurekaClient
   ```
### **In `application.properties` for Each Service**

Add the following configuration:
   ``` properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
   ```

---

## **Dynamic Port**

Instead of hardcoding the port, you can set the port as **0** to let Spring Boot select an available port during runtime.

```properties
server.port=0
```

