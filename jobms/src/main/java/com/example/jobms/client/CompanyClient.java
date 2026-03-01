package com.example.jobms.client;

import com.example.jobms.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("COMPANYMS")
public interface CompanyClient {
    @GetMapping("/company/{id}")
    Company getCompany(@PathVariable("id") Long id);

}
