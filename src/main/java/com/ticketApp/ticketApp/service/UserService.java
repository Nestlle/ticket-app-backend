package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.EventDTO;
import com.ticketApp.ticketApp.dto.UserDTO;
import com.ticketApp.ticketApp.dto.UserForSaveDTO;
import com.ticketApp.ticketApp.entity.TicketEntity;
import com.ticketApp.ticketApp.entity.UserEntity;
import com.ticketApp.ticketApp.repository.TicketRepository;
import com.ticketApp.ticketApp.repository.UserRepository;
import com.ticketApp.ticketApp.service.adapter.Adapter;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.nio.CharBuffer;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO getUserByID(Integer userID){
        UserEntity userFromDB = userRepository.findByUserID(userID);

        return authenticationService.findByLogin(userFromDB.getEmail());

 //       return Adapter.convertUserEntityToDTO(userRepository.getById(userID));
    }


//    public UserEntity saveUser(@Valid UserEntity user) throws Exception {
//        List<UserEntity> users = userRepository.findAll();
//        for(UserEntity u : users){
//            if(u.getEmail().equalsIgnoreCase(user.getEmail())){
//                throw new Exception("this email aldready exists");
//            }
//        }
//        return userRepository.save(user);
//    }

    public void saveUser(@Valid UserForSaveDTO user) throws Exception {
        List<UserEntity> users = userRepository.findAll();
        for(UserEntity u : users){
            if(u.getEmail().equalsIgnoreCase(user.getEmail())){
                throw new Exception("this email aldready exists");
            }
        }
        UserEntity userEntity = Adapter.convertUserSaveDTOToEntity(user);
        userEntity.setPassword(passwordEncoder.encode(CharBuffer.wrap(userEntity.getPassword())));
        userEntity.setRole("USER");

        userRepository.save(userEntity);
    }

}
