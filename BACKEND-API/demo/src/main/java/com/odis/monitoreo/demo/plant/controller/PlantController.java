package com.odis.monitoreo.demo.plant.controller;

import com.odis.monitoreo.demo.plant.models.Plant;
import com.odis.monitoreo.demo.plant.models.PlantRequest;
import com.odis.monitoreo.demo.plant.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Controlador REST para la gestión de plantas.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD sobre las plantas.
 * El acceso está protegido y depende de los roles del usuario:
 * <ul>
 *     <li><b>ROLE_ADMIN:</b> Acceso completo (GET, POST, PUT, DELETE).</li>
 *     <li><b>MONITOR, VIEWER:</b> Acceso de solo lectura (GET).</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
@Tag(name = "Plantas", description = "Gestión de plantas de monitoreo")
@SecurityRequirement(name = "bearerAuth")
public class PlantController {
    // Inyeccion de dependencias
    private final PlantService plantService;

//    /**
//     * Endpoint de bienvenida para verificar el acceso.
//     * @return Un mensaje de bienvenida.
//     */
//    @Operation(
//            summary = "Bienvenida",
//            description = "Endpoint de prueba para verificar acceso autenticado.",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Acceso correcto")
//            }
//    )
//    @GetMapping(value = "welcome")
//    public ResponseEntity<String> welcome(){
//        return ResponseEntity.ok("Hola desde un endpoint privado");
//    }

    /**
     * Obtiene una lista de todas las plantas a las que el usuario tiene acceso.
     * @PreAuthorize verifica que el usuario actual pertenzca a empresa ODIS para ver todas las plantas.
     * @return Un {@link ResponseEntity} con una lista de objetos {@link Plant}.
     */
    @Operation(
            summary = "Listar plantas",
            description = "Obtiene todas las plantas visibles para el usuario actual.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de plantas obtenida")
            }
    )
    @GetMapping
    @PreAuthorize("@securityChecker.isFromSuperCompany(principal.username)")
    public List<Plant> getAllPlants() {
        return plantService.getAllPlants();
    }

    /**
     * Obtiene una planta específica por su ID.
     * El acceso está restringido a usuarios que tienen la planta asignada.
     *
     * @param id El ID de la planta a obtener.
     * @return Un {@link ResponseEntity} con la planta si se encuentra, o 404 Not Found.
     */
    @Operation(
            summary = "Obtener planta por ID",
            description = "Busca una planta por su ID. Requiere permisos de acceso a la planta.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Planta encontrada"),
                    @ApiResponse(responseCode = "404", description = "Planta no encontrada o sin acceso")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("@securityChecker.userCompanyEqualsPlantCompany(principal.username, #id)")
    public Plant getPlantById(@PathVariable Integer id) {
        return plantService.getPlantById(id);
    }

    /**
     * Crea una nueva planta.
     * Endpoint protegido, solo accesible para usuarios con rol de administrador.
     *
     * @param plant El objeto {@link PlantRequest} a crear.
     * @return Un {@link String} este es el codigo de Acceso de la planta al API por el cual la planta
     * Se autentifica y permite conectarse desde un punto especifico para adminitir conexiones entrantes
     *
     */
    @Operation(
            summary = "Crear planta",
            description = "Registra una nueva planta. Retorna el código de acceso para la planta. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Planta creada exitosamente. Retorna token de acceso.")
            }
    )
    @PostMapping
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public String createPlant(@RequestBody PlantRequest plant)  {
        return plantService.createPlant(plant);
    }

    /**
     * Actualiza una planta existente.
     * Endpoint protegido, solo accesible para usuarios con rol de administrador.
     *
     * @param id El ID de la planta a actualizar.
     * @param plantDetails El objeto {@link PlantRequest} con los datos actualizados.
     * @return Un {@link ResponseEntity} con la planta actualizada, o 404 Not Found.
     */
    @Operation(
            summary = "Actualizar planta",
            description = "Modifica los datos de una planta existente. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Planta actualizada"),
                    @ApiResponse(responseCode = "404", description = "Planta no encontrada")
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public Plant updatePlant(@PathVariable Integer id, @RequestBody PlantRequest plantDetails) {
        return plantService.updatePlant(id, plantDetails);
    }

    /**
     * Elimina una planta por su ID.
     * Endpoint protegido, solo accesible para usuarios con rol de administrador.
     *
     * @param id El ID de la planta a eliminar.
     * @return Un {@link ResponseEntity} con estado 204 No Content si se eliminó, o 404 Not Found.
     */
    @Operation(
            summary = "Eliminar planta",
            description = "Elimina una planta del sistema. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Planta eliminada"),
                    @ApiResponse(responseCode = "404", description = "Planta no encontrada")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public ResponseEntity<Void> deletePlant(@PathVariable Integer id) {
        boolean deleted = plantService.deletePlant(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
