package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.EventDTO;
import com.ticketApp.ticketApp.dto.EventForSaveDTO;
import com.ticketApp.ticketApp.dto.EventTicketDto;
import com.ticketApp.ticketApp.dto.SearchDTO;
import com.ticketApp.ticketApp.entity.EventEntity;
import com.ticketApp.ticketApp.entity.TicketEntity;
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


    public Integer createEvent(EventForSaveDTO event) {
        EventEntity eventEntity = Adapter.convertEventForSaveDTOToEntity(event);

        System.out.println("ENTITY GET TIME: " + eventEntity.getStartTime());
        System.out.println("DTO GET time: " + event.getStartTime());
        if(event.getEventID() != null && event.getEventID() != 0){
            eventEntity.setEventID(event.getEventID());
        }
        eventRepository.save(eventEntity);
        List<EventTicketDto> ticketDtos = event.getTickets();
        for (EventTicketDto item:ticketDtos){
            TicketEntity ticketEntity;
            if(item.ticketID != null && item.ticketID != 0){
                ticketEntity = ticketRepository.getById(item.ticketID);
            }
            else{
                ticketEntity = new TicketEntity();
            }
            ticketEntity.setTicketNb(item.nbOfAvailableTickets);
            ticketEntity.setTicketType(item.description);
            ticketEntity.setEvent(eventEntity);
            ticketEntity.setPrice(item.price);
            ticketRepository.save(ticketEntity);
            item.setTicketID(ticketEntity.getTicketID());
        }

        return eventEntity.getEventID();
    }

    public EventForSaveDTO getEventByIDForAdmin(Integer eventID) {
        EventEntity event = eventRepository.getById(eventID);
        EventForSaveDTO eventDto = Adapter.convertEventEntityToEventForSaveDTO(event);
        List<EventTicketDto> ticketDtos = new ArrayList<>();

        for (TicketEntity item : event.getTickets()){
            EventTicketDto ticketDto = new EventTicketDto();
            ticketDto.ticketID = item.getTicketID();
            ticketDto.price = item.getPrice();
            ticketDto.nbOfAvailableTickets = item.getTicketNb();
            ticketDto.description = item.getTicketType();
            ticketDtos.add(ticketDto);
        }

        eventDto.setTickets(ticketDtos);

        return eventDto;
    }
}
