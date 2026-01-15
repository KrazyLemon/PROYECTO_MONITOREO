package com.odis.monitoreo.demo.config.Security;

import com.odis.monitoreo.demo.user.models.Role;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import com.odis.monitoreo.demo.user.models.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("securityChecker")
@AllArgsConstructor
public class SecurityChecker {

    private UserRepository userRepository;

    /*
    * Verifica que el usuario Tenga privilegios de administrador
    * y que pertenezca a la empresa Padre (ODIS)
    * */
    public boolean isSuperUser(String email) {
        System.out.println("Email:" + email );
        return userRepository.findByEmail(email)
                .map(user -> user.getRole() == Role.ROLE_ADMIN &&
                        user.getCompany() != null &&
                        user.getCompany().isSuperEmpresa())
                .orElse(false);
    }
}
