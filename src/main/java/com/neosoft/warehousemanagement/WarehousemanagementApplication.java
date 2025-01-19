package com.neosoft.warehousemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WarehousemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehousemanagementApplication.class, args);
	}

}
