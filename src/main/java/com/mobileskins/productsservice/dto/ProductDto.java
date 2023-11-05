package com.mobileskins.productsservice.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Component
public class ProductDto {
	
	private Long id;
	
	@NotBlank(message = "sku cannot be empty.")
	private String sku;

	@NotBlank(message = "Product title cannot be empty.")
	private String title;
	
	@NotNull(message = "Product price cannot be empty.")
	private BigDecimal price;
	
	private boolean trending;

	@NotNull(message = "Specify how many quantities of this product are available.")
	private int inStock;
	
	private String picName;
	
	private String picType;
	
	@JsonIgnore
	private byte[] picData;
	
}
