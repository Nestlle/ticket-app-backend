package com.ticketApp.ticketApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForSaveDTO {

//    private Integer userID;

    private String firstName;

    private String lastName;

    private String address;

    private String email;

    private String password;

    private String sex;
}
