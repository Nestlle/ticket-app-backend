package com.ticketApp.ticketApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewCartDTO {

    private String eventName;

    private Integer eventID;

    private Integer ticketID;

    private String ticketType;

    private Integer numberCartTickets;

    private byte[] photo;

    private Double ticketPrice;
}
