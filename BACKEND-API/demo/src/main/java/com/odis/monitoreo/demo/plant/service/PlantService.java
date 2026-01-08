package com.odis.monitoreo.demo.plant.service;

import com.odis.monitoreo.demo.plant.model.Plant;
import com.odis.monitoreo.demo.plant.repository.PlantRepository;
import com.odis.monitoreo.demo.user.models.User;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

// Inyeccion de dependencias
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    // Metodos
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Optional<Plant> getPlantById(Integer id) {
        return plantRepository.findById(id);
    }

    public Plant createPlant(Plant plant) {
        return plantRepository.save(plant);
    }

    @Transactional
    public Optional<Plant> updatePlant(Integer id, Plant plantDetails) {
        return plantRepository.findById(id)
                .map(plant -> {
                    plant.setName(plantDetails.getName());
                    plant.setUbication(plantDetails.getUbication());
                    plant.setVpnIp(plantDetails.getVpnIp());
                    plant.setIpVnc(plantDetails.getIpVnc());
                    return plantRepository.save(plant);
                });
    }

    @Transactional
    public boolean deletePlant(Integer id) {
        return plantRepository.findById(id)
                .map(plant -> {
                    plantRepository.delete(plant);
                    return true;
                }).orElse(false);
    }

//    public ResponseEntity<Plant> conect(Integer id, User user) {
//        ret
//    }
}
