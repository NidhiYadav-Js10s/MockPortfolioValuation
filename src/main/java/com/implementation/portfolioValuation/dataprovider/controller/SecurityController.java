package com.implementation.portfolioValuation.dataprovider.controller;

import com.implementation.portfolioValuation.dataprovider.datamodel.Security;
import com.implementation.portfolioValuation.dataprovider.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Security")
public class SecurityController
{
    @Autowired
    SecurityService securityService;
    @GetMapping( path = "/getAllSecurity")
    public List<Security> getAllSecurity()
    {
        return securityService.getAllSecurity();
    }
}
