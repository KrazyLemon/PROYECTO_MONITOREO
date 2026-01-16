package com.odis.monitoreo.demo.audit.controller;


import com.odis.monitoreo.demo.audit.models.LogActivity;
import com.odis.monitoreo.demo.audit.service.LogService;
import com.odis.monitoreo.demo.user.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para la consulta de logs de auditoría.
 * <p>
 * Proporciona endpoints para visualizar el historial de actividades del sistema.
 * El acceso está restringido a usuarios con privilegios de superusuario.
 */
@RestController
@RequestMapping("/api/logs")
@AllArgsConstructor
@Tag(name = "Auditoría", description = "Consulta de logs de actividad del sistema")
@SecurityRequirement(name = "bearerAuth")
public class LogController {
    private final LogService logService;

    /**
     * Obtiene todos los registros de actividad del sistema.
     * Requiere permisos de superusuario.
     *
     * @return Una lista completa de {@link LogActivity}.
     */
    @Operation(
            summary = "Listar todos los logs",
            description = "Obtiene el historial completo de actividades registradas en el sistema. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de logs obtenida exitosamente")
            }
    )
    @GetMapping
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public List<LogActivity> getAllLogs(){
        return logService.getAllLogs();
    }

    /**
     * Obtiene los registros de actividad filtrados por usuario.
     * Requiere permisos de superusuario.
     *
     * @param user El nombre de usuario o identificador para filtrar los logs.
     * @return Una lista de {@link LogActivity} correspondientes al usuario especificado.
     */
    @Operation(
            summary = "Listar logs por usuario",
            description = "Obtiene el historial de actividades realizadas por un usuario específico. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de logs del usuario obtenida exitosamente")
            }
    )
    @GetMapping("/{user}")
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public List<LogActivity> getLogsByUser(@PathVariable String user){
        return logService.getLogsByUser(user);
    }

}
