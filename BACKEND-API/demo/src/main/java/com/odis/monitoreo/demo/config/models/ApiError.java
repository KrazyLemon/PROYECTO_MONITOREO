package com.odis.monitoreo.demo.config.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Representa una estructura de respuesta de error estandarizada para la API.
 * Contiene información detallada sobre un error ocurrido durante el procesamiento de una solicitud.
 */
@Getter
@Setter
public class ApiError {
    /**
     * La marca de tiempo en la que ocurrió el error.
     */
    private LocalDateTime timestamp;
    /**
     * El código de estado HTTP.
     */
    private int status;
    /**
     * Una breve descripción del tipo de error (ej. "Not Found", "Bad Request").
     */
    private String error;
    /**
     * Un mensaje más detallado sobre la causa del error.
     */
    private String message;
    /**
     * La ruta (path) de la solicitud que originó el error.
     */
    private String path;

    /**
     * Construye una nueva instancia de ApiError.
     *
     * @param status  El código de estado HTTP.
     * @param error   Una breve descripción del error.
     * @param message Un mensaje detallado sobre el error.
     * @param path    La ruta de la solicitud que causó el error.
     */
    public ApiError(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
