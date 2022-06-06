package com.ticketApp.ticketApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventTicketDto {
    public Integer ticketID;
    public String description;
    public Integer nbOfAvailableTickets;
    public Double price;
}
