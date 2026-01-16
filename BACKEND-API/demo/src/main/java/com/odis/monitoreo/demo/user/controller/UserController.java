package com.odis.monitoreo.demo.user.controller;

import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * <p>
 * Proporciona endpoints para realizar operaciones CRUD sobre los usuarios.
 * El acceso está restringido a usuarios con privilegios de superusuario.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    
    private final UserService userService;

    /**
     * Obtiene una lista de todos los usuarios registrados.
     * Requiere permisos de superusuario.
     *
     * @return Una lista de objetos {@link User}.
     */
    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene todos los usuarios registrados en el sistema. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
            }
    )
    @GetMapping
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    /**
     * Obtiene un usuario específico por su ID.
     * Requiere permisos de superusuario.
     *
     * @param id El ID del usuario a buscar.
     * @return El objeto {@link User} encontrado.
     */
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Busca un usuario por su ID. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public User getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Requiere permisos de superusuario.
     *
     * @param user El objeto {@link User} con los datos del nuevo usuario.
     * @return El usuario creado.
     */
    @Operation(
            summary = "Registrar usuario",
            description = "Crea un nuevo usuario en el sistema. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente")
            }
    )
    @PostMapping
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Requiere permisos de superusuario.
     *
     * @param user El objeto {@link User} con los datos actualizados.
     * @param id El ID del usuario a actualizar.
     * @return El usuario actualizado.
     */
    @Operation(
            summary = "Actualizar usuario",
            description = "Modifica los datos de un usuario existente. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @PutMapping("/{id}")
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public User updateUser(@RequestBody User user, @PathVariable Integer id){
        return userService.updateUser(user, id);
    }

    /**
     * Elimina un usuario del sistema.
     * Requiere permisos de superusuario.
     *
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario fue eliminado, false en caso contrario.
     */
    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario del sistema por su ID. Requiere rol de superusuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("@securityChecker.isSuperUser(principal.username)")
    public boolean deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }
    

}
