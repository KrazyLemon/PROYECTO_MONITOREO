package com.odis.monitoreo.demo.plant.repository;

import com.odis.monitoreo.demo.plant.models.Conection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Conection, Integer> {
    Conection findByToken(String token);
}
