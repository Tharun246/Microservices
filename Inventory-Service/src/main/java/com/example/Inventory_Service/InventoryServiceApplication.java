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
	public CommandLineRunner loadData(InventoryRepo inventoryRepo) {
		return args -> {

			if (inventoryRepo.count() == 0)   // If no data exists, load it for the first time
			{
				Inventory inventory = new Inventory();
				inventory.setSkuCode("Iphone-13");
				inventory.setQuantity(4);
				inventoryRepo.save(inventory);

				Inventory inventory1 = new Inventory();
				inventory1.setSkuCode("Nothing_2a");
				inventory1.setQuantity(2);
				inventoryRepo.save(inventory1);

				Inventory inventory2=new Inventory();
				inventory.setQuantity(0);
				inventory.setSkuCode("Iphone_16");
				inventoryRepo.save(inventory2);
			}
		};
	}
}
