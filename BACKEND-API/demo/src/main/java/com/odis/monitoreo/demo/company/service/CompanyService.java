package com.odis.monitoreo.demo.company.service;

import com.odis.monitoreo.demo.company.models.Company;
import com.odis.monitoreo.demo.company.models.CompanyResponse;
import com.odis.monitoreo.demo.company.repository.CompanyRepository;
import com.odis.monitoreo.demo.config.SecurityUtils;
import com.odis.monitoreo.demo.user.models.Role;
import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

/**
 * Servicio encargado de la gestión de empresas (Company).
 * Proporciona métodos para crear, leer, actualizar y eliminar empresas.
 * <p>
 * Todas las operaciones están restringidas y requieren que el usuario autenticado
 * tenga el rol de ADMIN y pertenezca a una "SuperEmpresa" (ODIS).
 */
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    /*   *****************************************************************
     ************** API ENDPOINT SOLO ACCESIBLES PARA  ********************
     *
     * CUALQUIER ACCION RELACIONADA A PLANTAS, EMPRESAS QUE NO SEA CONECTARSE ESTA
     * PROHIBIDA PARA CUALQUIER USUARIO QUE NO CUMPLA CON LOS REQUISISTOS
     * ROLE == ADMIN
     * ID_EMPRESA == 1 (ODIS)
     *
     * ************************ ******************************************/

    /**
     * Obtiene una empresa por su identificador.
     *
     * @param id El identificador de la empresa.
     * @return Un Optional que contiene la empresa si se encuentra.
     * @throws AccessDeniedException Si el usuario actual no tiene permisos (no es ADMIN de SuperEmpresa).
     */
    @Transactional
    public List<CompanyResponse> getCompanyById(Integer id) throws AccessDeniedException{
        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));

        if(user.getCompany().isSuperEmpresa() && user.getRole() == Role.ROLE_ADMIN){
            return companyRepository.findById(id).stream()
                    .map(c -> new CompanyResponse(c.getId(), c.getName(), c.isSuperEmpresa()) )
                    .toList();
        }
        throw new AccessDeniedException("No tienes permiso de acceder a esos datos");
    }

    /**
     * Obtiene todas las empresas registradas.
     *
     * @return Una lista de todas las empresas.
     * @throws AccessDeniedException Si el usuario actual no tiene permisos (no es ADMIN de SuperEmpresa).
     */
    @Transactional
    public List<CompanyResponse> getAllCompanies() throws AccessDeniedException {
        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));

        if(user.getCompany().isSuperEmpresa() && user.getRole() == Role.ROLE_ADMIN){
            return companyRepository.findAll().stream()
                    .map(c -> new CompanyResponse(c.getId(), c.getName(), c.isSuperEmpresa()) )
                    .toList();
        }
        throw new AccessDeniedException("No tienes permiso de acceder a esos datos");
    }

    /**
     * Agrega una nueva empresa.
     *
     * @param company La entidad de la empresa a guardar.
     * @return La empresa guardada.
     * @throws AccessDeniedException Si el usuario actual no tiene permisos para realizar esta acción.
     */
    @Transactional
    public Company addCompany(Company company)throws AccessDeniedException {
        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));

        if(user.getCompany().isSuperEmpresa() && user.getRole() == Role.ROLE_ADMIN){
            return companyRepository.save(company);
        }
        throw new AccessDeniedException("No tienes permiso para realizar esta accion");
    }

    /**
     * Actualiza la información de una empresa existente.
     *
     * @param newcompany Los nuevos datos de la empresa.
     * @param id El identificador de la empresa a actualizar.
     * @return Un Optional con la empresa actualizada si existía.
     * @throws AccessDeniedException Si el usuario actual no tiene permisos para realizar esta acción.
     */
    @Transactional
    public Optional<Company> updateCompany(Company newcompany,Integer id)throws AccessDeniedException {
        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));

        if(user.getCompany().isSuperEmpresa() && user.getRole() == Role.ROLE_ADMIN){
            return companyRepository.findById(id)
                    .map(company -> {
                        company.setName(newcompany.getName());
                        return companyRepository.save(company);
                    });
        }
        throw new AccessDeniedException("No tienes permiso para realizar esta accion");
    }

    /**
     * Elimina una empresa por su identificador.
     *
     * @param id El identificador de la empresa a eliminar.
     * @return true si la operación se realizó correctamente.
     * @throws AccessDeniedException Si el usuario actual no tiene permisos para realizar esta acción.
     */
    @Transactional
    public boolean deleteCompany( Integer id) throws AccessDeniedException{
        User principal = securityUtils.getCurrentUser();
        User user = userRepository.findById(principal.getId()).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));

        if(user.getCompany().isSuperEmpresa() && user.getRole() == Role.ROLE_ADMIN){
            companyRepository.deleteById(id);
            return true;
        }
        throw new AccessDeniedException("No tienes permiso para realizar esta accion");
    }


}
