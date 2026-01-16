package com.odis.monitoreo.demo.plant.service;

import com.odis.monitoreo.demo.company.models.Company;
import com.odis.monitoreo.demo.company.repository.CompanyRepository;
import com.odis.monitoreo.demo.config.aplication.KeyGeneratorUtils;
import com.odis.monitoreo.demo.config.Security.SecurityUtils;
import com.odis.monitoreo.demo.plant.models.Plant;
import com.odis.monitoreo.demo.plant.models.PlantRequest;
import com.odis.monitoreo.demo.plant.repository.PlantRepository;
import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlantService {

    // Inyeccion de dependencias
    private final PlantRepository plantRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;


    // Metodos
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public Plant getPlantById(Integer id) {
        return plantRepository.findById(id).orElseThrow();
    }

    public List<Plant> getPlantsByCompanyId(Integer companyId){
        Company company = companyRepository.findById(companyId).orElseThrow();
        return plantRepository.findByCompanyId(companyId);
    }

    @Transactional
    public String createPlant(PlantRequest plant) throws RuntimeException {
        String rawKey = KeyGeneratorUtils.generateKey();
        String encodedKey = passwordEncoder.encode(rawKey);
        Company company = companyRepository.findByName(plant.getCompany());

        Plant newPlant = new Plant();

        newPlant.setName(plant.getName());
        newPlant.setKey(encodedKey);
        newPlant.setCompany(company);
        newPlant.setUbication(plant.getUbication());
        newPlant.setVpnIp(plant.getVpnIp());
        newPlant.setIpVnc(plant.getIpVnc());

        plantRepository.save(newPlant);
        return rawKey;
    }

    @Transactional
    public Plant updatePlant(Integer id, PlantRequest plantDetails) throws RuntimeException {
        plantRepository.findById(id).orElseThrow(() -> new RuntimeException("Planta no Encontrada"));
        Company company = company = companyRepository.findByName(plantDetails.getCompany());

        Plant plant = new Plant();

        plant.setName(plantDetails.getName());
        plant.setUbication(plantDetails.getUbication());
        plant.setVpnIp(plantDetails.getVpnIp());
        plant.setIpVnc(plantDetails.getIpVnc());
        plant.setCompany(company);


        return plantRepository.save(plant);
    }

    @Transactional
    public boolean deletePlant(Integer id) {
        Plant plant = plantRepository.findById(id).orElseThrow(() -> new RuntimeException("Planta no Encontrada"));
        plantRepository.delete(plant);
        return true;
    }
}
