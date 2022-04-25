package com.ticketApp.ticketApp.controller;


import com.ticketApp.ticketApp.dto.TicketDTO;
import com.ticketApp.ticketApp.dto.UserDTO;
import com.ticketApp.ticketApp.dto.UserForSaveDTO;
import com.ticketApp.ticketApp.entity.UserEntity;
import com.ticketApp.ticketApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-profile")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{userID}")
        public UserDTO getUser(@PathVariable("userID") Integer id) {
        return userService.getUserByID(id);
    }

    @PostMapping("/signup")
    public void saveUser(@RequestBody UserForSaveDTO user) throws Exception {
         userService.saveUser(user);
    }
}
