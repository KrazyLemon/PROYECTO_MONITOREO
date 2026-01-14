package com.odis.monitoreo.demo.company.controller;

import com.odis.monitoreo.demo.company.models.Company;
import com.odis.monitoreo.demo.company.models.CompanyResponse;
import com.odis.monitoreo.demo.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de empresas.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las empresas.
 * El acceso a estos endpoints está restringido y requiere permisos de administrador.
 */
@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Empresas", description = "Gestión de empresas (CRUD)")
@SecurityRequirement(name = "bearerAuth")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Obtiene una lista de todas las empresas.
     *
     * @return Una lista de objetos {@link Company}.
     * @throws AccessDeniedException Si el usuario no tiene los permisos necesarios.
     */
    @Operation(
            summary = "Listar empresas",
            description = "Obtiene todas las empresas registradas. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de empresas obtenida correctamente"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    @GetMapping
    public List<CompanyResponse> getAllCompanies() throws AccessDeniedException{
        return  companyService.getAllCompanies();
    }

    /**
     * Obtiene una empresa por su ID.
     *
     * @param id El ID de la empresa a obtener.
     * @return Un {@link Optional} que contiene la empresa si se encuentra.
     * @throws AccessDeniedException Si el usuario no tiene los permisos necesarios.
     */
    @Operation(
            summary = "Obtener empresa por ID",
            description = "Busca una empresa específica por su identificador. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    @GetMapping("/{id}")
    public List<CompanyResponse>getCompanyById(@PathVariable Integer id)throws AccessDeniedException{
        return companyService.getCompanyById(id);
    }

    /**
     * Agrega una nueva empresa.
     *
     * @param company El objeto {@link Company} a agregar.
     * @return La empresa que fue agregada.
     * @throws AccessDeniedException Si el usuario no tiene los permisos necesarios.
     */
    @Operation(
            summary = "Crear empresa",
            description = "Registra una nueva empresa en el sistema. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa creada exitosamente"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    @PostMapping
    public Company addCompany(@RequestBody Company company)throws AccessDeniedException{
        return companyService.addCompany(company);
    }

    /**
     * Actualiza una empresa existente.
     *
     * @param company El objeto {@link Company} con los datos actualizados.
     * @param id El ID de la empresa a actualizar.
     * @return Un {@link Optional} que contiene la empresa actualizada si se encuentra.
     * @throws AccessDeniedException Si el usuario no tiene los permisos necesarios.
     */
    @Operation(
            summary = "Actualizar empresa",
            description = "Modifica los datos de una empresa existente. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa actualizada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    @PutMapping("/{id}")
    public Optional<Company> updateCompany(@RequestBody Company company, @PathVariable Integer id)throws AccessDeniedException{
        return companyService.updateCompany(company, id);
    }

    /**
     * Elimina una empresa por su ID.
     *
     * @param id El ID de la empresa a eliminar.
     * @return {@code true} si la empresa fue eliminada.
     * @throws AccessDeniedException Si el usuario no tiene los permisos necesarios.
     */
    @Operation(
            summary = "Eliminar empresa",
            description = "Elimina una empresa del sistema por su ID. Requiere rol ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa eliminada correctamente"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    @DeleteMapping("/{id}")
    public boolean deleteCompany(@PathVariable Integer id)throws AccessDeniedException{
        return companyService.deleteCompany(id);
    }

}
