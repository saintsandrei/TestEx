package com.example.mynewapp.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.mynewapp.service.TokenService;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;




@Service
public class TokenServiceImpl implements TokenService {
    @Value("${auth.jwt.secret}")
    private String secretKey;
    private Logger log;

    @Override
    public String generateToken(String login) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = Instant.now();
        Instant exp = now.plus(60, ChronoUnit.MINUTES);
        return JWT.create()
                .withIssuer("auth-service")
                .withAudience("store")
                .withSubject(login)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
    }

    @Override
    public boolean checkToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-service")){
                log.error("Issuer is incorrect");
                return false;
            }
            if (!decodedJWT.getAudience().contains("store")){
                log.error("Audience is incorrect");
                return false;
            }

        }catch (JWTVerificationException e){
            log.error("Token is invalid: " + e.getMessage());
            return false;
        }
        return true;
    }
}
