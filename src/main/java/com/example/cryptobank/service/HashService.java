package com.example.cryptobank.service;

import com.example.cryptobank.security.HashHelper;
import com.example.cryptobank.security.HashAndSalt;
import com.example.cryptobank.security.SaltMaker;
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
    private Logger logger = LoggerFactory.getLogger(SaltMaker.class);
    private final int DEFAULT_ROUNDS = 10;
    private final int DEFAULT_ITERATIONS = 2;
    private final int DEFAULT_MEM_SIZE = 15 * 1024;
    private final int DEFAULT_PARALLEL_DEG = 1;
    private final PepperService pepperService;
    private final Argon2 argon2;
    private int rounds;
    private int iterations;
    private int memSize;
    private int parallelDeg;

    @Autowired
    public HashService(PepperService pepperService){
        super();
        logger.info("New HashService");
        this.pepperService = pepperService;
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        setRounds(DEFAULT_ROUNDS);
        setIterations(DEFAULT_ITERATIONS);
        setMemSize(DEFAULT_MEM_SIZE);
        setParallelDeg(DEFAULT_PARALLEL_DEG);
    }

    /**
     * get pepper, hash salted and peppered password
     * process number of rounds and return final hash
     * @param password (String)
     * @return (String)
     */
    public String hash(String password, String salt){
        String hash = HashHelper.hash(pepperService.getPepper(), password, salt);
        return processRounds(hash, rounds);
    }

    /**
     * Generate salt, get pepper, hash salted and peppered password
     * process number of rounds and return both hashed password and salt
     * @param password (String)
     * @return (HashAndSalt) object containing hashed password and generated salt
     */
    public HashAndSalt hash(String password){
        SaltMaker saltMaker = new SaltMaker();
        String salt = saltMaker.generateSalt();
        String hash = HashHelper.hash(pepperService.getPepper(), password, salt);
        return new HashAndSalt(processRounds(hash, rounds), salt);
    }

    /**
     * Hash peppered password using Argon2id
     * Argon2id generates and embeds salt in hash
     * format: $argon2id$v=(version)$m=(allocated memory),t=(iterations), p=(degrees of parallelism)$(salt)$(hash)
     * @param password (String)
     * @return (HashAndSalt) Argon2 hash and generated salt
     */
    public HashAndSalt argon2idHash(String password){
        String pepperedPw = pepperService.getPepper() + password;
        String hash = argon2.hash(iterations, memSize, parallelDeg, pepperedPw);
        String[] hashArgs = hash.split("\\$");
        return new HashAndSalt(hash, hashArgs[hashArgs.length - 2]);
    }

    /**
     * Pepper password and verify using argon2.verify()
     * @param hash (String) full Argon2 hash (containing args and embedded salt)
     * @param password (String)
     * @return (boolean)
     */
    public boolean argon2idVerify(String hash, String password){
        return argon2.verify(hash, pepperService.getPepper() + password);
    }

    /**
     * Process password key stretching
     * @param passwordHash (String)
     * @param rounds (int)
     * @return (String)
     */
    private String processRounds(String passwordHash, int rounds) {
        String rehash = passwordHash;
        for (int i = 0; i < rounds; i++)
            rehash = HashHelper.hash(rehash);
        return rehash;
    }

    public void setRounds(int rounds){
        this.rounds = rounds;
    }

    public int getRounds(){
        return this.rounds;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setParallelDeg(int parallelDeg) {
        this.parallelDeg = parallelDeg;
    }

    public void setMemSize(int memSize) {
        this.memSize = memSize;
    }

}
