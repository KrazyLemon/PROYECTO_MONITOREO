package com.odis.monitoreo.demo.plant.repository;

import com.odis.monitoreo.demo.plant.model.Conection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConectionRepository extends JpaRepository<Conection, Integer> {
}
