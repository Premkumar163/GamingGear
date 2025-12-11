package com.GamingGear.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;

@Service
public class Gamingservice {
	
	@Autowired
	GamingRepository gamingRepository;
	
   //Save Data
	public Forminputs savedata(Forminputs g) {
		return gamingRepository.save(g);
		
	}
	
	//Get Data
	public List<Forminputs> dataget() {
		return gamingRepository.findAll();
	}
	
}
