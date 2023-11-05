package com.mobileskins.productsservice.controller;

import java.util.Optional;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.mobileskins.productsservice.dto.ProductDto;
import com.mobileskins.productsservice.entity.Product;
import com.mobileskins.productsservice.exception.ResourceNotFoundException;
import com.mobileskins.productsservice.service.ProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
@RequestMapping("/api/products/")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Value("${projectimage}")
	String path;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("create")
	public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDto productDto) {
		
		Optional<ProductDto> savedProductDto = productService.createProduct(productDto);
		
		if(savedProductDto.isPresent())
			return new ResponseEntity<>(savedProductDto.get(), HttpStatus.OK);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't save the user.");
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createmany")
	public ResponseEntity<?> createManyProducts(@RequestBody List<@Valid ProductDto> productDtoList) {
		
		List<ProductDto> savedProductDtoList = productService.createProducts(productDtoList);
		
		if(savedProductDtoList.isEmpty())
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't save any of the users.");
		
		return new ResponseEntity<>(savedProductDtoList, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<?> getProducts() {
		
		List<ProductDto> productDtos = productService.getProducts();
		
		if(productDtos.isEmpty())
			throw new ResourceNotFoundException("Internal error : Couldn't fetch the products");
		
		return new ResponseEntity<>(productDtos, HttpStatus.OK);
		
	}
	
	@GetMapping("/getBysku/{product-sku}")
	public ResponseEntity<?> getProductBySku(@PathVariable("product-sku") String sku) {
		
		ProductDto productDto = productService.getProductDtoBySku(sku);
		
		return new ResponseEntity<>(productDto, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody @Valid ProductDto productDto) {
		
		System.out.println(productDto.toString());
		
		ProductDto updatedProductDto = productService.updateProduct(productDto);
		
		System.out.println("updatedProductDto : " + updatedProductDto.toString());
		
		return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/updateProducts")
	public ResponseEntity<?> updateManyProducts(@RequestBody List<@Valid ProductDto> productDtoList) {
		
		List<ProductDto> updatedProductDtos = productService.updateManyProducts(productDtoList);
		
		return new ResponseEntity<>(updatedProductDtos, HttpStatus.OK);
		
	}
	

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	@DeleteMapping("/delete/{sku}")
	public ResponseEntity<?> deleteProduct(@PathVariable("sku") String sku) {
		
		String result = productService.deleteProduct(sku);
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteAll")
	public String deleteAllProducts() {
		return productService.deleteAllProducts();
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/uploadPic/{sku}")
	public ResponseEntity<?> saveProductImage(@RequestParam("image") MultipartFile image, @PathVariable("sku") String sku) {
		ProductDto productDto = productService.uploadImage(path, image, sku);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}
	

	@GetMapping("/getImage/{sku}")
	public ResponseEntity<Resource> getProductImage(@PathVariable("sku") String sku) {
		
		Product product = productService.getProductBySku(sku);
		
		return ResponseEntity.ok()
							 .contentType(MediaType.parseMediaType(product.getPicType()))
							 .body(new ByteArrayResource(product.getPicData()));
		
	}
}
