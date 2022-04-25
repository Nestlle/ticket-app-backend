package com.ticketApp.ticketApp.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Time;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class ViewReservationDTO {

    private String eventName;

    private Integer eventID;

    private Integer ticketID;

    private String ticketType;

    private Integer numberReservedTickets;

    private byte[] photo;

    private Double ticketPrice;

    private Date eventDate;

    private LocalTime eventTime;
}
