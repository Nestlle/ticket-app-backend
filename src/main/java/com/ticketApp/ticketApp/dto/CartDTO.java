package com.ticketApp.ticketApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private Integer ticketID;

    private Integer userID;

    private Integer numberOfTickets;

}
