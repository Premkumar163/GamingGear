package com.GamingGear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GamingGear.model.Forminputs;
import com.GamingGear.repository.GamingRepository;

@Service
public class Gamingservice {
	
	@Autowired
	GamingRepository gamingRepository;
	

	public Forminputs savedata(Forminputs g) {
		return gamingRepository.save(g);
		
	}
	
}
