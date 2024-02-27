package com.implementation.portfolioValuation.dataprovider.service;

import com.implementation.portfolioValuation.dataprovider.datamodel.Security;
import com.implementation.portfolioValuation.dataprovider.datamodel.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityService {

    @Autowired
    SecurityRepository securityRepository;
    public List<Security> getAllSecurity()
    {
        List<Security> securities = new ArrayList<>();
        securityRepository.findAll().forEach(security -> securities.add(security));
        return securities;
    }


}
