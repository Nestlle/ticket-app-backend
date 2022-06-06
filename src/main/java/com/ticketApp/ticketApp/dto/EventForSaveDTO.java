package com.ticketApp.ticketApp.dto;

import com.ticketApp.ticketApp.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class EventForSaveDTO {

    @NotNull(message = "event ID cannot be null")
    private Integer eventID;

    @NotNull(message = "event name cannot be null")
    private String eventName;

    @NotNull(message = "event location cannot be null")
    private String location;

    private String description;

    @NotNull(message = "event date cannot be null")
    private Date date;

    @NotNull(message = "event start time cannot be null")
    private LocalTime startTime;


    private byte[] photo;

    private Integer categoryID;

    private List<EventTicketDto> tickets;
}
