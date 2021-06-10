package com.example.cryptobank.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.mkammerer.argon2.*;

/**
 * Service that provides hashed passwords and generated salts, pepper included
 * Allows use of SHA-256 with key stretching or Argon2id for hashing
 * Use setters to update number of rounds or Argon2 arguments
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
    private int iterations;
    private int memSize;
    private int parallelDeg;

    @Autowired
    public HashService(PepperService pepperService){
        super();
        logger.info("New HashService");
        this.pepperService = pepperService;
        argon2id = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        setIterations(DEFAULT_ITERATIONS);
        setMemSize(DEFAULT_MEM_SIZE);
        setParallelDeg(DEFAULT_PARALLEL_DEG);
    }

    /**
     * Hash peppered password using Argon2id
     * Argon2id generates and embeds salt in hash
     * format: $argon2id$v=(version)$m=(allocated memory),t=(iterations), p=(degrees of parallelism)$(salt)$(hash)
     * @param password (String)
     * @return (HashAndSalt) Argon2 hash and generated salt
     */
    public String argon2idHash(String password){
        String pepperedPw = pepperService.getPepper() + password;
        return argon2id.hash(iterations, memSize, parallelDeg, pepperedPw);
    }

    /**
     * Pepper password and verify using argon2.verify()
     * @param hash (String) full Argon2 hash (containing args and embedded salt)
     * @param password (String)
     * @return (boolean)
     */
    public boolean argon2idVerify(String hash, String password){
        return argon2id.verify(hash, pepperService.getPepper() + password);
    }

    public void setIterations(int iterationsNr) {
        this.iterations = iterationsNr;
    }

    public void setParallelDeg(int parallelDegNr) {
        this.parallelDeg = parallelDegNr;
    }

    public void setMemSize(int memSizeAmount) {
        this.memSize = memSizeAmount * MB_KB_CONVERSION;
    }

    public int getIterations() {
        return iterations;
    }

    public int getMemSize() {
        return memSize;
    }

    public int getParallelDeg() {
        return parallelDeg;
    }
}
