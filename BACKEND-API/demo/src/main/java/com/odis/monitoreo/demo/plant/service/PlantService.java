package com.odis.monitoreo.demo.plant.service;

import com.odis.monitoreo.demo.company.models.Company;
import com.odis.monitoreo.demo.company.repository.CompanyRepository;
import com.odis.monitoreo.demo.config.aplication.KeyGeneratorUtils;

import com.odis.monitoreo.demo.plant.models.AccessLevel;
import com.odis.monitoreo.demo.plant.models.ConnectionRequest;
import com.odis.monitoreo.demo.plant.models.Plant;
import com.odis.monitoreo.demo.plant.models.PlantRequest;
import com.odis.monitoreo.demo.plant.repository.PlantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PlantService {

    // Inyeccion de dependencias
    private final PlantRepository plantRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;
    private final ConnectionService connectionService;

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
    public Plant createPlant(PlantRequest plant) throws RuntimeException {

        // Creamos la planta
        String rawKey = KeyGeneratorUtils.generateKey();
        Company company = companyRepository.findByName(plant.getCompany()); // Buscamos la empresa asociada a la planta

        Plant newPlant = new Plant();
        newPlant.setName(plant.getName());
        newPlant.setCompany(company);
        newPlant.setToken(rawKey);
        newPlant.setUbication(plant.getUbication());
        newPlant.setVpnIp(plant.getVpnIp());
        newPlant.setIpVnc(plant.getIpVnc());

        plantRepository.save(newPlant);
        // Creamos conexión con el usuario loggeado actualmente
        ConnectionRequest connectionRequest = new ConnectionRequest(
                newPlant.getId(),
                AccessLevel.ADMIN_ACCESS,
                LocalDateTime.now().plusDays(30)
        );

        connectionService.createConnection(connectionRequest);

        return newPlant;
    }

    @Transactional
    public Plant updatePlant(Integer id, PlantRequest plantDetails) throws RuntimeException {

        Plant plant = plantRepository.findById(id).orElseThrow(() -> new RuntimeException("Planta no Encontrada"));
        Company company  = companyRepository.findByName(plantDetails.getCompany());

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
