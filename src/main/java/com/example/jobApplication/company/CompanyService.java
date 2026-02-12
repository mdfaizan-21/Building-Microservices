package com.example.jobApplication.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    void addCompany(Company company);
    boolean remove(Long id);
    Company updateCompanyById(Long id,Company company);
    Company getCompanyById(Long id);
}
