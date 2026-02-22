package com.example.companyms.company.impl;

import com.example.companyms.company.Company;
import com.example.companyms.company.CompanyRepository;
import com.example.companyms.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;


    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean remove(Long id) {
        if(getCompanyById(id)!=null){
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company updateCompanyById(Long id, Company company) {
        if(getCompanyById(id)!=null){
            Company companyToUpdate=new Company();
            companyToUpdate.setName(company.getName());
            companyToUpdate.setId(id);
            companyToUpdate.setDescription(company.getDescription());
//            companyToUpdate.setJobs(company.getJobs());
//            companyToUpdate.setReview(company.getReview());

            companyRepository.save(companyToUpdate);
            return companyToUpdate;
        }
        return null;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
