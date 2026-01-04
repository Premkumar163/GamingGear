//package com.GamingGear.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.Optional;
//
//import com.GamingGear.model.Forminputs;
//
//public interface GamingRepository extends JpaRepository<Forminputs, Long> {
//
//    boolean existsByEmail(String email);
//
//    Optional<Forminputs> findByEmail(String email);
//    Object findByEmailOrMobilenoOrUsername(String identifier, String identifier2, String identifier3);
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
