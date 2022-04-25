package com.ticketApp.ticketApp.controller;

import com.ticketApp.ticketApp.dto.EventDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.CharBuffer;
import java.util.List;

@RestController
@RequestMapping("/api/password")
public class PasswordGeneratorController {

    private final PasswordEncoder passwordEncoder;

    public PasswordGeneratorController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{password}")
    public String getPass(@PathVariable final String password){
        return passwordEncoder.encode(CharBuffer.wrap(password));
    }

}
