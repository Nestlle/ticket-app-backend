package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.EventDTO;
import com.ticketApp.ticketApp.dto.EventForSaveDTO;
import com.ticketApp.ticketApp.dto.SearchDTO;
import com.ticketApp.ticketApp.entity.EventEntity;
import com.ticketApp.ticketApp.repository.EventRepository;
import com.ticketApp.ticketApp.repository.TicketRepository;
import com.ticketApp.ticketApp.service.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<EventDTO> getAllEvents() {
        List<EventDTO> events = Adapter.convertEventEntitiesToDTOs(eventRepository.findAll());
        List<EventDTO> eventsAfterToday = new ArrayList<>();
        Date today = new Date();
        for (EventDTO event : events) {
            if (event.getDate().after(today)) {
                eventsAfterToday.add(event);
            }
        }
        return eventsAfterToday;
    }

    public EventDTO getEventByID(Integer eventID) {
        EventEntity event = eventRepository.getById(eventID);
//        List<TicketEntity> ticketEntities = ticketRepository.findByEvent(eventID);
//        TicketEntity ticketEntity = ticketRepository.findByEvent(event);

        EventDTO eventDTO = Adapter.convertEventEntityToDTO(event);
//        List<TicketDTO> ticketDTOS = Adapter.convertTicketEntitiesToDTOs(ticketEntities);
//        TicketDTO ticket = Adapter.convertTicketEntityToDTO(ticketEntity);
//        eventDTO.setTicket(ticket);
        return eventDTO;
    }

    public List<EventDTO> getEventsBySearchCriteria(SearchDTO searchCriteria) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return Adapter.convertEventEntitiesToDTOs(eventRepository.getEventsBySearchCriteria
                (searchCriteria.location, formatter.parse(searchCriteria.from), formatter.parse(searchCriteria.to), searchCriteria.category));
    }

    public EventDTO getEventByTicketID(Integer ticketId) {
        EventEntity event = eventRepository.getEventByTicketID(ticketId);

        EventDTO eventDTO = Adapter.convertEventEntityToDTO(event);

        return eventDTO;
    }


    public void createEvent(EventForSaveDTO event) {
        EventEntity eventEntity = Adapter.convertEventForSaveDTOToEntity(event);

        System.out.println("ENTITY GET TIME: " + eventEntity.getStartTime());
        System.out.println("DTO GET time: " + event.getStartTime());

        eventRepository.save(eventEntity);
    }
}
