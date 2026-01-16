package com.odis.monitoreo.demo.audit.component;

import com.odis.monitoreo.demo.audit.models.LogActivity;
import com.odis.monitoreo.demo.audit.models.TipoAccion;
import com.odis.monitoreo.demo.audit.repositories.LogActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AccionAuditAspect {
    private final LogActivityRepository logRepo;

    @Pointcut("execution(* com.tuapp.service.*.*(..))")
    public void todosLosServicios() {

    }

    @AfterReturning(pointcut = "todosLosServicios()", returning = "result")
    public void auditarSucces(JoinPoint joinPoint, Object result) {
        try {
            String nombreMetodo = joinPoint.getSignature().getName();
            String nombreClase = joinPoint.getTarget().getClass().getSimpleName();

            // String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String usuario = "ANON";

            if(auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)){
                usuario = auth.getName();
            }

            TipoAccion accion = mapearMetodoAAccion(nombreMetodo);

            LogActivity log = new LogActivity(
                    usuario,
                    accion,
                    "Ejecutó con éxito: " + nombreClase + "." + nombreMetodo + " | Retorno: " + (result != null ? result.toString() : "void"),
                    LocalDateTime.now()
            );

            logRepo.save(log);
        } catch (Exception e) {
            log.warn("Fallo auditoria, request continua: ", e);
        }
    }

    @AfterThrowing(pointcut = "todosLosServicios()", throwing = "exception")
    public void auditarError(JoinPoint joinPoint, Exception exception) {
        try {
            String usuario = "ANON";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                usuario = auth.getName();
            }
            String nombreMetodo = joinPoint.getSignature().getName();

            LogActivity log = new LogActivity(
                    usuario,
                    TipoAccion.ERROR_SISTEMA,
                    "FALLO en el método " + nombreMetodo + ". Error: " + exception.getMessage(),
                    LocalDateTime.now()
            );
            logRepo.save(log);
        } catch (Exception e) {
            log.warn("Fallo auditoria, request continua: ",e);
        }
    }

    private TipoAccion mapearMetodoAAccion(String metodo) {
        if (metodo.startsWith("get") || metodo.startsWith("find") || metodo.startsWith("list")) {
            return TipoAccion.CONSULTA;
        } else if (metodo.startsWith("save") || metodo.startsWith("create")) {
            return TipoAccion.CREACION_ENTIDAD;
        } else if (metodo.startsWith("update")) {
            return TipoAccion.ACTUALIZACION_ENTIDAD;
        } else if (metodo.startsWith("delete")) {
            return TipoAccion.ELIMINACION_ENTIDAD;
        }
        return TipoAccion.CONSULTA;
    }
}
