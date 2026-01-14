package com.odis.monitoreo.demo.plant.service;

import com.odis.monitoreo.demo.config.KeyGeneratorUtils;
import com.odis.monitoreo.demo.config.SecurityUtils;
import com.odis.monitoreo.demo.plant.model.Conection;
import com.odis.monitoreo.demo.plant.model.ConnectionRequest;
import com.odis.monitoreo.demo.plant.model.Plant;
import com.odis.monitoreo.demo.plant.repository.ConnectionRepository;
import com.odis.monitoreo.demo.plant.repository.PlantRepository;
import com.odis.monitoreo.demo.user.models.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final SecurityUtils securityUtils;
    private final PlantRepository plantRepository;

    @Transactional
    public Conection getConnection(String token){
        return connectionRepository.findByToken(token);
    }

    @Transactional
    public String createConnection(ConnectionRequest connectionRequest){

        User usr = securityUtils.getCurrentUser();
        Plant plant  = plantRepository.findById(connectionRequest.getPlantId()).orElseThrow();
        String token = KeyGeneratorUtils.generateConnectionToken();

        Conection conn = new Conection();
        conn.setUser(usr);
        conn.setPlant(plant);
        conn.setAccess(connectionRequest.getLevel());
        conn.setToken(token);
        conn.setLastConexion(LocalDateTime.now());
        conn.setActive(true);
        conn.setExpirationTime(connectionRequest.getExpirationTime());
        connectionRepository.save(conn);

        return token;
    }

    @Transactional
    public String deleteConnection(String token) {
        Conection conn = connectionRepository.findByToken(token);
        if (conn != null) {
            conn.setActive(false);
            connectionRepository.save(conn);
            return "Conexion Eliminada";
        }
        return "Conexion no encontrada";
    }

    @Transactional
    public String updateConnection(String token, ConnectionRequest connectionRequest){
        Conection conn = connectionRepository.findByToken(token);
        if (conn != null) {
            conn.setAccess(connectionRequest.getLevel());
            conn.setExpirationTime(connectionRequest.getExpirationTime());
            connectionRepository.save(conn);
            return "Conexion Actualizada";
        }
        return "Conexion no encontrada";
    }

}