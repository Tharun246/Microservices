package com.example.Inventory_Service;

import com.example.Inventory_Service.Model.Inventory;
import com.example.Inventory_Service.Repository.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepo inventoryRepo)
	{
		return args -> {


			Inventory inventory = new Inventory();

			inventory.setSkuCode("Iphone-13");
			inventory.setQuantity(20);

			Inventory inventory1 = new Inventory();

			inventory1.setSkuCode("Nothing 2a");
			inventory1.setQuantity(2);

			inventoryRepo.save(inventory);
			inventoryRepo.save(inventory1);
		};
	}
}
