package com.odis.monitoreo.demo.plant.controller;

import com.odis.monitoreo.demo.plant.models.Conection;
import com.odis.monitoreo.demo.plant.models.ConnectionRequest;
import com.odis.monitoreo.demo.plant.service.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de conexiones a plantas.
 * <p>
 * Proporciona endpoints para crear, obtener, actualizar y eliminar conexiones
 * utilizando tokens de acceso.
 */
@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
@Tag(name = "Conexiones", description = "Gestión de conexiones a plantas")
@SecurityRequirement(name = "bearerAuth")
public class ConnectionController {
    private final ConnectionService connectionService;

    /**
     * Obtiene una conexión específica utilizando su token.
     *
     * @param token El token de la conexión.
     * @return El objeto {@link Conection} correspondiente al token.
     */
    @Operation(
            summary = "Obtener conexión por token",
            description = "Busca y retorna una conexión válida utilizando su token de acceso.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conexión encontrada"),
                    @ApiResponse(responseCode = "404", description = "Conexión no encontrada o token inválido")
            }
    )
    @GetMapping
    public Conection getConnectionByToken(@RequestParam String token ){
        return connectionService.getConnection(token);
    }

    /**
     * Crea una nueva conexión.
     *
     * @param connectionRequest Objeto con los detalles para crear la conexión.
     * @return Un token o identificador de la conexión creada.
     */
    @Operation(
            summary = "Crear conexión",
            description = "Genera una nueva conexión basada en la solicitud proporcionada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conexión creada exitosamente")
            }
    )
    @PostMapping
    public String createConnection(@RequestBody ConnectionRequest connectionRequest){
        return connectionService.createConnection(connectionRequest);
    }

    /**
     * Elimina o invalida una conexión existente.
     *
     * @param token El token de la conexión a eliminar.
     * @return Mensaje de confirmación de la eliminación.
     */
    @Operation(
            summary = "Eliminar conexión",
            description = "Invalida o elimina una conexión activa utilizando su token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conexión eliminada exitosamente")
            }
    )
    @PutMapping("/delete")
    public String deleteConnection(@RequestParam String token){
        return connectionService.deleteConnection(token);
    }

    /**
     * Actualiza una conexión existente.
     *
     * @param token El token de la conexión a actualizar.
     * @param connectionRequest Objeto con los nuevos detalles de la conexión.
     * @return Mensaje de confirmación de la actualización.
     */
    @Operation(
            summary = "Actualizar conexión",
            description = "Actualiza los parámetros de una conexión existente identificada por su token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conexión actualizada exitosamente")
            }
    )
    @PutMapping("/update")
    public String updateConnection(@RequestParam String token, @RequestBody ConnectionRequest connectionRequest){
        return connectionService.updateConnection(token, connectionRequest);
    }

}
