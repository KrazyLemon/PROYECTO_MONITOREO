package com.odis.monitoreo.demo.company.repository;

import com.odis.monitoreo.demo.company.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
