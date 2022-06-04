package com.ticketApp.ticketApp.util;

import com.ticketApp.ticketApp.dto.CredentialsDTO;
import com.ticketApp.ticketApp.dto.UserDTO;
import com.ticketApp.ticketApp.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {

    private String secretKey = "my-secret-key";

    private final AuthenticationService authenticationService;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 360000000);

        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("Roles", new String[]{"ROLE_ADMIN", "ROLE_USER"})
                .compact();
    }

    public Authentication validateToken(String token) {
        String login = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDTO user = authenticationService.findByLogin(login);
        user.setToken(token);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateCredentials(CredentialsDTO credentialsDto) {
        UserDTO user = authenticationService.authenticate(credentialsDto);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}
