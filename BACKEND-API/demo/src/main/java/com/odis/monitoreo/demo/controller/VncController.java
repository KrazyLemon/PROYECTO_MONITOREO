package com.odis.monitoreo.demo.controller;

import com.odis.monitoreo.demo.repository.VncTokenRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vnc")
public class VncController {
    @Autowired
    private VncTokenRepository tokenRepository;

    @GetMapping("/validate")
    public ResponseEntity<String> validate(@RequestParam String token){
        return tokenRepository.findByTokenAndActiveTrue(token)
                .filter(t -> !t.isExpired())
                .map(t -> ResponseEntity.ok(t.getTargetIp() + ":"+ t.getTargetPort() ))
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }
}
