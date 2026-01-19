package com.odis.monitoreo.demo.auth.controller;

import com.odis.monitoreo.demo.auth.models.AuthResponse;
import com.odis.monitoreo.demo.auth.models.LoginRequest;
import com.odis.monitoreo.demo.auth.models.RegisterRequest;
import com.odis.monitoreo.demo.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la autenticación de usuarios.
 * <p>
 * Proporciona endpoints públicos para el inicio de sesión y el registro de nuevos usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios")
public class AuthController {

    private final AuthService authService;

    /**
     * Inicia sesión de un usuario existente.
     *
     * @param request Objeto {@link LoginRequest} que contiene las credenciales del usuario (username y password).
     * @return Un {@link ResponseEntity} con la respuesta de autenticación {@link AuthResponse}, que incluye el token JWT.
     */
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica a un usuario con sus credenciales y devuelve un token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
                    @ApiResponse(responseCode = "403", description = "Credenciales inválidas")
            }
    )
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param request Objeto {@link RegisterRequest} con los datos del nuevo usuario.
     * @return Un {@link ResponseEntity} con la respuesta de autenticación {@link AuthResponse}, que incluye el token JWT generado.
     */
    @Operation(
            summary = "Registrar usuario",
            description = "Registra un nuevo usuario en el sistema y devuelve un token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Registro exitoso"),
                    @ApiResponse(responseCode = "400", description = "Datos de registro inválidos")
            }
    )
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }



}
