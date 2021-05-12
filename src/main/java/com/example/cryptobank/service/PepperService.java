package com.example.cryptobank.service;

import com.example.cryptobank.security.SaltMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Temporary utility class for pepper storage
 * @author David_Scager
 */
@Service
public class PepperService {
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private static final String PEPPER = "27d05704";

    @Autowired
    public PepperService(){
        super();
        logger.info("New PepperService");
    }

    public String getPepper(){
        return PEPPER;
    }
}
