package com.odis.monitoreo.demo.audit.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "usuario")
    private String user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "accion")
    private TipoAccion action;

    @Column(length = 500, name = "detalle")
    private String details;

    @Column(name = "fecha")
    private LocalDateTime date;

    public LogActivity(String user, TipoAccion action, String details,LocalDateTime date){
        this.user = user;
        this.action = action;
        this.details = details;
        this.date = date;
    }

}
