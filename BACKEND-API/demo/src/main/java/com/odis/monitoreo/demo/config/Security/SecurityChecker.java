package com.odis.monitoreo.demo.config.Security;

import com.odis.monitoreo.demo.plant.repository.PlantRepository;
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
    private PlantRepository plantRepository;

    /*
    * Verifica que el usuario Tenga privilegios de administrador
    * y que pertenezca a la empresa Padre (ODIS)
    * */
    public boolean isSuperUser(String email) {
        // System.out.println("Email:" + email );
        return userRepository.findByEmail(email)
                .map(user -> user.getRole() == Role.ROLE_ADMIN &&
                        user.getCompany() != null &&
                        user.getCompany().isSuperEmpresa())
                .orElse(false);
    }

    public boolean isFromSuperCompany(String email){
        return userRepository.findByEmail(email)
                .map(user -> user.getCompany() != null &&
                        user.getCompany().isSuperEmpresa())
                .orElse(false);
    }

    public boolean userCompanyEqualsPlantCompany(String email, Integer plantId){

        int plantCompanyId = plantRepository.findById(plantId).get().getCompany().getId();
        int userCompanyId = userRepository.findByEmail(email).get().getCompany().getId();

        return plantCompanyId == userCompanyId || isFromSuperCompany(email);

    }
}
