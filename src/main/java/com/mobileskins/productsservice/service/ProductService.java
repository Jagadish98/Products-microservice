package com.mobileskins.productsservice.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mobileskins.productsservice.dto.ProductDto;
import com.mobileskins.productsservice.entity.Product;
import com.mobileskins.productsservice.exception.FileNotFoundException;
import com.mobileskins.productsservice.exception.ResourceNotFoundException;
import com.mobileskins.productsservice.exception.SkuAlreadyExistsException;
import com.mobileskins.productsservice.repository.ProductRepository;



@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public Optional<ProductDto> createProduct(ProductDto productDto) {
		
		//Check if product already exists in DB. If exists, don't save the product.
		Optional<Product> exisitingProduct = productRepository.findBySku(productDto.getSku());
		
		if(exisitingProduct.isPresent())
			throw new SkuAlreadyExistsException("Product", "sku", productDto.getSku());
		
		//Convert ProductDto to Product to save in DB.
		Product product = modelMapper.map(productDto, Product.class);
		
		Product savedProduct = productRepository.save(product);
		
		return Optional.ofNullable(modelMapper.map(savedProduct, ProductDto.class));
		
	}

	
	public List<ProductDto> createProducts(List<ProductDto> productDtoList) {
		
		List<ProductDto> productDtos = productDtoList.stream()
													 .map(productDto -> createProduct(productDto).get())
													 .collect(Collectors.toList());
	
		return productDtos;
		
	}
	
	public List<ProductDto> getProducts(){
		
		List<Product> products = productRepository.findAll();
		
		List<ProductDto> productDtos = products.stream()
											   .map(product -> modelMapper.map(product, ProductDto.class))
											   .collect(Collectors.toList());
		
		return productDtos;
		
	}
	
	public ProductDto getProductDtoBySku(String sku) {
		
		Product product = productRepository.findBySku(sku)
				                           .orElseThrow( () -> new ResourceNotFoundException("Product", "sku", sku));
		
		return modelMapper.map(product, ProductDto.class);
		
	}
	
	public Product getProductBySku(String sku) {
		
		Product product = productRepository.findBySku(sku)
				                           .orElseThrow( () -> new ResourceNotFoundException("Product", "sku", sku));
		
		return product;
		
	}
	
	public ProductDto updateProduct(ProductDto productDto) {
		
		ProductDto existingProductDto = getProductDtoBySku(productDto.getSku());
		
		Product product = modelMapper.map(existingProductDto, Product.class);
		
		product.setTitle(productDto.getTitle());
		product.setSku(productDto.getSku());
		product.setPrice(productDto.getPrice());
		product.setInStock(productDto.getInStock());
		if(productDto.isTrending())
			product.setTrending(true);
		
		product = productRepository.save(product);		
		return modelMapper.map(product, ProductDto.class);
		
	}
	
	public List<ProductDto> updateManyProducts(List<ProductDto> productDtos) {
		
		List<ProductDto> updatedProductDtos = productDtos.stream()
														  .map(productDto -> updateProduct(productDto))
														  .collect(Collectors.toList());
		
		return updatedProductDtos;
		
	}
	
	public String deleteProduct(String sku) {
		
		productRepository.findBySku(sku)
				   		 .orElseThrow(() -> new ResourceNotFoundException("Product", "sku", sku));
		
		productRepository.deleteBySku(sku);
		return "Product deleted successfully.";
	}
	
	public String deleteAllProducts() {
		
		productRepository.deleteAll();
		return "Deleted all products.";
		
	}
	
	public ProductDto uploadImage(String path, MultipartFile imageFile, String sku)  {
		
		Product product = getProductBySku(sku);
		
		String filePath = path + File.separator + imageFile.getOriginalFilename();
		
		try {
			product.setPicName(imageFile.getOriginalFilename());
			product.setPicType(imageFile.getContentType());
			product.setPicData(imageFile.getBytes());
			Product savedProduct = productRepository.save(product);
			return modelMapper.map(savedProduct, ProductDto.class);
		} catch (IOException e) {
		    throw new FileNotFoundException("Couldn't create directory " + Paths.get(filePath).toAbsolutePath() + " As it already exists or error creating directory.");
		}
		
	}
	
}
