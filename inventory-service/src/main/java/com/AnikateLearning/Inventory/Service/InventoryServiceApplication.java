package com.AnikateLearning.Inventory.Service;

import com.AnikateLearning.Inventory.Service.Repository.InventoryRepository;
import com.AnikateLearning.Inventory.Service.model.Inventory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
	SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository)
	{
		return args -> {
			Inventory inventory=new Inventory();
			inventory.setSkuCode("iphone13");
			inventory.setQuantity(100);

			Inventory inventory1=new Inventory();
			inventory1.setSkuCode("iphone13red");
			inventory1.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);

		};
	}


}
