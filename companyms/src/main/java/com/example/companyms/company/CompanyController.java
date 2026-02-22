package com.example.companyms.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
     CompanyService companyService;
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompany(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Company> getCompany(@PathVariable Long id){
        Company company=companyService.getCompanyById(id);
        if(company!=null){
            return  ResponseEntity.ok(company);
        }
        return  ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<Company> addCompany(@RequestBody Company newCompany){
        companyService.addCompany(newCompany);
        return ResponseEntity.ok(newCompany);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Company> updateCompany(@RequestBody Company company,@PathVariable Long id){
        if(companyService.updateCompanyById(id,company)!=null){
            return  ResponseEntity.ok(company);
        }
        return  ResponseEntity.status(404).body(null);
    }


    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCompany(@PathVariable Long id){
        if(companyService.remove(id)){
            return ResponseEntity.ok("Company Removed Successfully");
        }
        return  ResponseEntity.status(404).body("Company with this company id does not exist");
    }
}
