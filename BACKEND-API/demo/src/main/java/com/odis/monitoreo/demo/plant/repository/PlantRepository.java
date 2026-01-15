package com.odis.monitoreo.demo.plant.repository;

import com.odis.monitoreo.demo.plant.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Integer> {
    Optional<Plant> findByName(String name);
}
