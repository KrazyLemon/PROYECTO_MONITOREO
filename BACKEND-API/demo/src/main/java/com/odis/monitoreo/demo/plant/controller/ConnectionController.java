package com.odis.monitoreo.demo.plant.controller;

import com.odis.monitoreo.demo.plant.model.Conection;
import com.odis.monitoreo.demo.plant.model.ConnectionRequest;
import com.odis.monitoreo.demo.plant.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connections")
@RequiredArgsConstructor
public class ConnectionController {
    private final ConnectionService connectionService;

    @GetMapping
    public Conection getConnectionByToken(@RequestParam String token ){
        return connectionService.getConnection(token);
    }

    @PostMapping
    public String createConnection(@RequestBody ConnectionRequest connectionRequest){
        return connectionService.createConnection(connectionRequest);
    }

    @PutMapping("/delete")
    public String deleteConnection(@RequestParam String token){
        return connectionService.deleteConnection(token);
    }

    @PutMapping("/update")
    public String updateConnection(@RequestParam String token, @RequestBody ConnectionRequest connectionRequest){
        return connectionService.updateConnection(token, connectionRequest);

    }

}
