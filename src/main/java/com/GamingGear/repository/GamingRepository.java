package com.GamingGear.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GamingGear.model.Forminputs;

public interface GamingRepository extends JpaRepository<Forminputs, Integer> {

}
