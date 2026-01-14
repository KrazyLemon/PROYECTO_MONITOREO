package com.odis.monitoreo.demo.plant.service;

import com.odis.monitoreo.demo.config.KeyGeneratorUtils;
import com.odis.monitoreo.demo.config.SecurityUtils;
import com.odis.monitoreo.demo.plant.model.Plant;
import com.odis.monitoreo.demo.plant.model.PlantRequest;
import com.odis.monitoreo.demo.plant.repository.PlantRepository;
import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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

    public Optional<Plant> getPlantById(Integer id) {
        return plantRepository.findById(id);
    }

    public String createPlant(PlantRequest plant) throws AccessDeniedException {
        String rawKey = KeyGeneratorUtils.generateKey();
        String encodedKey = passwordEncoder.encode(rawKey);

        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));

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
}
