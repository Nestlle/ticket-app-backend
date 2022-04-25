package com.ticketApp.ticketApp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketApp.ticketApp.dto.CredentialsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final UserAuthenticationProvider userAuthenticationProvider;

    public UsernamePasswordAuthFilter(UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

//        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
//        String basicAuthPart = header.split(" ")[1];
//        byte[] decodedBytes = Base64.getDecoder().decode(basicAuthPart);
//        String decodedString = new String(decodedBytes);
//        String[] decodedStringParts = decodedString.split(":");

        if ("/v1/signIn".equals(httpServletRequest.getServletPath())
                && HttpMethod.POST.matches(httpServletRequest.getMethod())) {
            CredentialsDTO credentialsDto = MAPPER.readValue(httpServletRequest.getInputStream(), CredentialsDTO.class);
//            CredentialsDTO credentialsDto = new CredentialsDTO(decodedStringParts[0], decodedStringParts[1].toCharArray());


            try {
                SecurityContextHolder.getContext().setAuthentication(
                        userAuthenticationProvider.validateCredentials(credentialsDto));
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
