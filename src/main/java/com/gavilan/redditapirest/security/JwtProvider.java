package com.gavilan.redditapirest.security;

import com.gavilan.redditapirest.exception.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception ocurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal = (User)authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationInMillis))
                .compact();
    }

    public String generateTokenWithUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(getPrivateKey())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationInMillis))
                .compact();
    }

    private PrivateKey getPrivateKey() {

        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException exception) {
            throw new SpringRedditException("Exception ocurred while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublickKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublickKey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception ocurred while retrieveng public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = parser()
                .setSigningKey(getPublickKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

}
