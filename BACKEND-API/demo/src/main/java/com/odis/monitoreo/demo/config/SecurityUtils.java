package com.odis.monitoreo.demo.config;

import com.odis.monitoreo.demo.user.models.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class SecurityUtils {

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            try {
                throw new AccessDeniedException("Usuario no autenticado");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }else {
            return (User) auth.getPrincipal();
        }
    }

}
