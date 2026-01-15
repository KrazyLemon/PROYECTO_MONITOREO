package com.odis.monitoreo.demo.plant.service;

import com.odis.monitoreo.demo.config.aplication.KeyGeneratorUtils;
import com.odis.monitoreo.demo.config.Security.SecurityUtils;
import com.odis.monitoreo.demo.plant.models.Plant;
import com.odis.monitoreo.demo.plant.models.PlantRequest;
import com.odis.monitoreo.demo.plant.repository.PlantRepository;
import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlantService {

    // Inyeccion de dependencias
    private final PlantRepository plantRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    // Metodos
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Plant getPlantById(Integer id) {
        return plantRepository.findById(id).orElseThrow();
    }

    public String createPlant(PlantRequest plant) {
        String rawKey = KeyGeneratorUtils.generateKey();
        String encodedKey = passwordEncoder.encode(rawKey);

        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Plant newPlant = new Plant();

        newPlant.setName(plant.getName());
        newPlant.setKey(encodedKey);
        newPlant.setCompany(user.getCompany());
        newPlant.setUbication(plant.getUbication());
        newPlant.setVpnIp(plant.getVpnIp());
        newPlant.setIpVnc(plant.getIpVnc());

        plantRepository.save(newPlant);
        return rawKey;
    }

    @Transactional
    public Plant updatePlant(Integer id, PlantRequest plantDetails) {
        plantRepository.findById(id).orElseThrow(() -> new RuntimeException("Planta no Encontrada"));

        Plant plant = new Plant();

        plant.setName(plantDetails.getName());
        plant.setUbication(plantDetails.getUbication());
        plant.setVpnIp(plantDetails.getVpnIp());
        plant.setIpVnc(plantDetails.getIpVnc());

        return plantRepository.save(plant);
    }

    @Transactional
    public boolean deletePlant(Integer id) {
        return plantRepository.findById(id)
                .map(plant -> {
                    plantRepository.delete(plant);
                    return true;
                }).orElse(false);
    }
}
