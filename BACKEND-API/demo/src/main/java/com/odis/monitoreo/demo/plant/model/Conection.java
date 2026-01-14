package com.odis.monitoreo.demo.plant.model;

import com.odis.monitoreo.demo.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Table(name = "conexiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "planta_id")
    private Plant plant;

    @Column(name = "token")
    private String token;

    @Column(name = "permiso")
    @Enumerated(EnumType.STRING)
    private AccessLevel access;

    @Column(name = "ultima_conexion")
    private LocalDateTime lastConexion;

    @Column(name ="esta_activo")
    private boolean isActive;

    @Column(name="expiracion")
    private LocalDateTime expirationTime;

}
