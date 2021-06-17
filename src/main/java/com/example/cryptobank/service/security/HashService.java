package com.example.cryptobank.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.mkammerer.argon2.*;

/**
 * Service that hashes and verifies passwords using Argon2id algorithm
 * @author David_Scager (Argon2id implementation), Reyndert_Mehrer, Huib_van_Straten
 */
@Service
public class HashService {
    private final Logger logger = LoggerFactory.getLogger(HashService.class);
    private static final int DEFAULT_ITERATIONS = 2;
    private static final int DEFAULT_MEM_SIZE = 15;
    private static final int DEFAULT_PARALLEL_DEG = 1;
    private static final int MB_KB_CONVERSION = 1024;
    private final PepperService pepperService;
    private final Argon2 argon2id;

    @Autowired
    public HashService(PepperService pepperService){
        super();
        logger.info("New HashService");
        this.pepperService = pepperService;
        argon2id = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    /**
     * Hash peppered password
     * Argon2id generates and embeds salt in hash
     * format: $argon2id$v=(version)$m=(allocated memory),t=(iterations), p=(degrees of parallelism)$(salt)$(hash)
     * @param password (String)
     * @return (HashAndSalt) Argon2 hash and generated salt
     */
    public String argon2idHash(String password){
        String pepperedPw = pepperService.getPepper() + password;
        String passwordHash = argon2id.hash(DEFAULT_ITERATIONS, DEFAULT_MEM_SIZE * MB_KB_CONVERSION, DEFAULT_PARALLEL_DEG, pepperedPw);
        logger.info("password hashed");
        return passwordHash;
    }

    /**
     * Pepper password and verify
     * @param hash (String)
     * @param password (String)
     * @return (boolean)
     */
    public boolean argon2idVerify(String hash, String password){
        boolean verified = argon2id.verify(hash, pepperService.getPepper() + password);
        logger.info("passwordHash verified");
        return verified;
    }

    public int getIterations() {
        return DEFAULT_ITERATIONS;
    }

    public int getMemSize() {
        return DEFAULT_MEM_SIZE * MB_KB_CONVERSION;
    }

    public int getParallelDeg() {
        return DEFAULT_PARALLEL_DEG;
    }
}
