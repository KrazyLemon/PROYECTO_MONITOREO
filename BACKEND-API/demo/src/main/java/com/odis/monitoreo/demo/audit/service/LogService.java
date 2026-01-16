package com.odis.monitoreo.demo.audit.service;

import com.odis.monitoreo.demo.audit.models.LogActivity;
import com.odis.monitoreo.demo.audit.repositories.LogActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LogService {
    private final LogActivityRepository repository;

    public List<LogActivity> getAllLogs(){
        return repository.findAllByDateDesc();
    }

    public List<LogActivity> getLogsByUser(String user){
        return repository.findByUserOrderByDateDesc(user);
    }

}
