package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.CredentialsDTO;
import com.ticketApp.ticketApp.dto.UserDTO;
import com.ticketApp.ticketApp.entity.UserEntity;
import com.ticketApp.ticketApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO authenticate(CredentialsDTO credentialsDto) {
        String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap("the-password"));
        UserEntity userEntity = userRepository.getByEmail(credentialsDto.getLogin());
        //UserEntity = UserRepository.getByEmail(credentialsDto.login);
        //
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), userEntity.getPassword())) {
            return new UserDTO(userEntity.getUserID(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getAddress(),
                    userEntity.getSex(), userEntity.getEmail(), "token", userEntity.getRole());
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDTO findByLogin(String login) {
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.getByEmail(login));
        if (userEntity.isPresent()) {
            return new UserDTO(userEntity.get().getUserID(), userEntity.get().getFirstName(), userEntity.get().getLastName(),
                    userEntity.get().getAddress(), userEntity.get().getSex(), userEntity.get().getEmail(), "token", "ADMIN");
        }
        throw new RuntimeException("Invalid login");
    }
}
