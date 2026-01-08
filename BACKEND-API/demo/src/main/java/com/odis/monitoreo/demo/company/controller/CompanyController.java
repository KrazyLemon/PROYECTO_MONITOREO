package com.odis.monitoreo.demo.company.controller;

import com.odis.monitoreo.demo.company.models.Company;
import com.odis.monitoreo.demo.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController("/api/Companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping
    public List<Company> getAllCompanies() throws AccessDeniedException{
        return  companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Optional<Company>getCompanyById(@PathVariable Integer id)throws AccessDeniedException{
        return companyService.getCompanyById(id);
    }

    @PostMapping("/{id}")
    public Company addCompany(@RequestBody Company company)throws AccessDeniedException{
        return companyService.addCompany(company);
    }

    @PutMapping("/{id}")
    public Optional<Company> updateCompany(@RequestBody Company company, @PathVariable Integer id)throws AccessDeniedException{
        return companyService.updateCompany(company, id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCompany(@PathVariable Integer id)throws AccessDeniedException{
        return companyService.deleteCompany(id);
    }

}
