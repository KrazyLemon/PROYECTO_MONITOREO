package com.odis.monitoreo.demo.audit.repositories;

import com.odis.monitoreo.demo.audit.models.LogActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogActivityRepository extends JpaRepository<LogActivity, Long> {
    List<LogActivity> findByUserOrderByDateDesc(String user);
}
