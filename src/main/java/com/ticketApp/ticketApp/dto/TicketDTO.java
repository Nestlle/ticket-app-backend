package com.ticketApp.ticketApp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketDTO {

   // @NotNull(message = "ticket ID cannot be null")
    private Integer ticketID;

//    @NotNull(message = "event ID cannot be null")
//    private Integer eventID;

    @NotNull(message = "price cannot be null")
    private double price;

    private String ticketType;

    private Integer ticketNb;

    private Integer ticketCounter;
//
//    @NotNull(message = "user ID cannot be null")
//    @NotEmpty(message = "user ID cannot be empty")
//    private Integer userID;
}
