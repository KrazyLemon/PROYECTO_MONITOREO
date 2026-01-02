package com.odis.monitoreo.demo.plant.controller;

import com.odis.monitoreo.demo.plant.model.Plant;
import com.odis.monitoreo.demo.plant.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
public class PlantController {
    // Inyeccion de dependencias
    private final PlantService plantService;

    @GetMapping(value = "welcome")
    public ResponseEntity<String> publico(){
        return ResponseEntity.ok("Hola desde un endpoint privado");
    }

//    @GetMapping
//    public ResponseEntity<List<Plant>> getAllPlants() {
//        return ResponseEntity.ok(plantService.getAllPlants());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable Integer id) {
        return plantService.getPlantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Plant> createPlant(@RequestBody Plant plant) {
        return ResponseEntity.status(201).body(plantService.createPlant(plant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plant> updatePlant(@PathVariable Integer id, @RequestBody Plant plantDetails) {
        return plantService.updatePlant(id, plantDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Integer id) {
        boolean deleted = plantService.deletePlant(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
