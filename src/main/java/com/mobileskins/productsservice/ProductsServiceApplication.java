package com.mobileskins.productsservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		
		info=@Info(
				title = "Product Service REST APIs",
				description = "Product Service REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Jagadish M",
						email = "jagadishgowda.m1@gmail.com"
						)
				)
		
		)
@SpringBootApplication
@EnableJpaRepositories("com.mobileskins.productsservice.repository")
@EntityScan("com.mobileskins.productsservice.entity")
@ComponentScan({"com.mobileskins.productsservice.controller", "com.mobileskins.productsservice.dto",
	             "com.mobileskins.productsservice.service", "com.mobileskins.productsservice.exception",
	             "com.mobileskins.productsservice.config"})

public class ProductsServiceApplication {
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

}
