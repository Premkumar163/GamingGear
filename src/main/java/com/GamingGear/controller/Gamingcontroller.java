package com.GamingGear.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.GamingGear.model.Forminputs;
import com.GamingGear.service.Gamingservice;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class Gamingcontroller {

	@Autowired
	Gamingservice gamingservice;

	@PostMapping("/save")
	public Forminputs save(@RequestBody Forminputs g) {
		return gamingservice.savedata(g);
		
	}
	
	@GetMapping("/all")
	public List<Forminputs> get() {
		return gamingservice.dataget();
	}
	
}
