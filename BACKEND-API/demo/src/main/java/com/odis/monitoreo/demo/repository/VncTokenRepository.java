package com.odis.monitoreo.demo.repository;


import com.odis.monitoreo.demo.entity.VncToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VncTokenRepository extends JpaRepository<VncToken, String> {

    Optional<VncToken> findByTokenAndActiveTrue(String token);
}

