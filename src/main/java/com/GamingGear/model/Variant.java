package com.GamingGear.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="variants")
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class Variant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long productId;
	
	private String sku;
	
	private Double price;
	
	private Integer stock;
	
	private Boolean isActive=true;

}
