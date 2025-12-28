//package com.GamingGear.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import com.GamingGear.model.Forminputs;
//
//public interface GamingRepository extends JpaRepository<Forminputs, Long> {
//
//	 Optional<Forminputs> findByEmailOrMobilenoOrUsername(
//	            String email,
//	            String mobileno,
//	            String username
//	    );
//	 
//	  Optional<Forminputs> findByEmail(String email);
//
//	boolean existsByEmail(String email);
//}




package com.GamingGear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.GamingGear.model.Forminputs;

public interface GamingRepository extends JpaRepository<Forminputs, Long> {

    boolean existsByEmail(String email);
    Optional<Forminputs> findByMobileno(String mobileno);


    Optional<Forminputs> findByEmail(String email);
   
    Optional<Forminputs> findByEmailOrMobilenoOrUsername(String email, String mobileno, String username);

}
