package com.ticketApp.ticketApp.controller;

import com.ticketApp.ticketApp.dto.EventDTO;
import com.ticketApp.ticketApp.dto.EventForSaveDTO;
import com.ticketApp.ticketApp.dto.SearchDTO;
import com.ticketApp.ticketApp.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/all")
    public List<EventDTO> getEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventID}")
    public EventDTO getEventByID(@PathVariable final Integer eventID) {
        return eventService.getEventByID(eventID);
    }

    @GetMapping("ticket/{ticketID}")
    public EventDTO getEventByTicketID(@PathVariable final Integer ticketID) {
        return eventService.getEventByTicketID(ticketID);
    }

    @PostMapping("/admin-event")
    public void createEvent(@RequestBody EventForSaveDTO event) {
        eventService.createEvent(event);
    }

    @PostMapping()
    public List<EventDTO> search(@RequestBody SearchDTO search) throws ParseException {
        return eventService.getEventsBySearchCriteria(search);
    }
}
