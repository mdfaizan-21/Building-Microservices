package com.example.companyms.company;

import com.example.companyms.dto.CompanyWithReviewAndJobsDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyWithReviewAndJobsDTO> getAllCompanies();
    List<Company> getAllCompany();
    void addCompany(Company company);
    boolean remove(Long id);
    Company updateCompanyById(Long id,Company company);
    Company getCompanyById(Long id);
}
