package com.mobileskins.productsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobileskins.productsservice.entity.Product;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findBySku(String sku);

	void deleteBySku(String sku);
	
}
