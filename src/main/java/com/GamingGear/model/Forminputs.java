package com.GamingGear.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userdoc")
public class Forminputs {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true,unique = true)
	private String username;
	
	@Column(nullable = false,unique = true)
	private	String email;
	
	@Column(nullable = true,unique = true)
	private String mobileno;
	
	@Column(nullable = false)
	private	String pass;
	
	private boolean emailVerified = false;
	private boolean phoneVerified= false;
	
	private String Status = "PENDING"; // PENDING or A

	
	

	
	
}
