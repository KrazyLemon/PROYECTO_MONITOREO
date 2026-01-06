package com.odis.monitoreo.demo.config;

import com.odis.monitoreo.demo.audit.models.LogActivity;
import com.odis.monitoreo.demo.audit.models.TipoAccion;
import com.odis.monitoreo.demo.audit.repositories.LogActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SecurityAuditListener {
    private final LogActivityRepository logRepo;

    @EventListener
    public void onLoginSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String ip = getClientIp(event.getAuthentication().getDetails());

        LogActivity log = new LogActivity(
                username,
                TipoAccion.LOGIN_EXITOSO,
                "Sesión iniciada correctamente desde IP: " + ip,
                LocalDateTime.now()
        );
        logRepo.save(log);
    }


    @EventListener
    public void onLoginFailure(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        String ip = getClientIp(event.getAuthentication().getDetails());
        String causa = event.getException().getMessage(); // Ej: "Bad credentials"

        LogActivity log = new LogActivity(
                username,
                TipoAccion.LOGIN_FALLIDO,
                "Intento fallido. Razón: " + causa + " | IP: " + ip,
                LocalDateTime.now()
        );
        logRepo.save(log);
    }

    private String getClientIp(Object details) {
        if (details instanceof WebAuthenticationDetails) {
            return ((WebAuthenticationDetails) details).getRemoteAddress();
        }
        return "IP Desconocida";
    }
}
