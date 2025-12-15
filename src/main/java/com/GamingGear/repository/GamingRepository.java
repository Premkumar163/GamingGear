package com.GamingGear.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.GamingGear.model.Forminputs;

public interface GamingRepository extends JpaRepository<Forminputs, Long> {

	Optional<Forminputs> findByUsername(String username);

	Optional<Forminputs> findByEmail(String email);

	Optional<Forminputs> findByMobileno(String mobileno);

	boolean existsByEmail(String email);
}
