package com.example.jobms.client;

import com.example.jobms.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEWMS"
        ,url = "${reviewms.url}")
public interface ReviewClient {
    @GetMapping("/review")
    List<Review>getReviews(@RequestParam("companyId") Long companyId);
}
