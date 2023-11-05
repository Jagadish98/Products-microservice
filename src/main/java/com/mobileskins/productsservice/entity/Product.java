package com.mobileskins.productsservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Entity
@Table(name = "products", schema = "mobileskins" , uniqueConstraints = {
		@UniqueConstraint(columnNames = "sku")
})
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String sku;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private boolean trending;

	private int inStock;
	
	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime lastUpdated;
	
	private String picName;
	
	private String picType;
	
	@Lob
	@Column(length = 50000000) 
	private byte[] picData;
	
}
