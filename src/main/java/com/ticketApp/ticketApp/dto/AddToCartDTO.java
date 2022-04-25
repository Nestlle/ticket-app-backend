package com.ticketApp.ticketApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDTO {

    private Integer userId;

    private List<TicketDTO> boughtTickets;


}
