package com.odis.monitoreo.demo.config.aplication;

import com.odis.monitoreo.demo.config.models.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Controlador global para el manejo de excepciones en la aplicación.
 * Captura excepciones específicas y genéricas para devolver respuestas de error estructuradas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo NoResourceFoundException (404 Not Found).
     * Se invoca cuando no se encuentra un recurso estático o ruta solicitada.
     *
     * @param ex         La excepción capturada.
     * @param webRequest La solicitud web actual.
     * @return ResponseEntity con un objeto ApiError y estado HTTP 404.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NoResourceFoundException ex, WebRequest webRequest){
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                ex.getMessage(),
                webRequest.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones de tipo IllegalArgumentException (400 Bad Request).
     * Se invoca cuando se pasan argumentos inválidos a un método o endpoint.
     *
     * @param ex  La excepción capturada.
     * @param web La solicitud web actual.
     * @return ResponseEntity con un objeto ApiError y estado HTTP 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex, WebRequest web){
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Solicitud incorrecta",
                ex.getMessage(),
                web.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja cualquier otra excepción no capturada específicamente (500 Internal Server Error).
     * Actúa como un manejador genérico para errores inesperados del servidor.
     *
     * @param ex  La excepción capturada.
     * @param web La solicitud web actual.
     * @return ResponseEntity con un objeto ApiError y estado HTTP 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest web) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                "Ocurrio un error inesperado en el servidor",
                web.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones de tipo AccessDeniedException (403 Forbidden).
     * Se invoca cuando un usuario autenticado intenta acceder a un recurso sin los permisos necesarios.
     *
     * @param ex      La excepción capturada.
     * @param request La solicitud web actual.
     * @return ResponseEntity con un objeto ApiError y estado HTTP 403.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "Acceso denegado: permisos insuficientes",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

}
