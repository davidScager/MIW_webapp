package com.example.cryptobank.service.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Temporary utility class for pepper storage
 * @author David_Scager, Reyndert_Mehrer, Huib_van_Straten
 */
@Service
public class PepperService {
    private static final String PEPPER = "27d05704";

    @Autowired
    public PepperService(){
        super();
        LoggerFactory.getLogger(PepperService.class).info("New PepperService");
    }

    public String getPepper(){
        return PEPPER;
    }
}
