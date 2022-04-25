package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.TicketDTO;
import com.ticketApp.ticketApp.entity.TicketEntity;
import com.ticketApp.ticketApp.repository.TicketRepository;
import com.ticketApp.ticketApp.service.adapter.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<TicketDTO> getTickets(){
        return Adapter.convertTicketEntitiesToDTOs(ticketRepository.findAll());
    }

    public TicketDTO findTicketById(Integer id) {
        return Adapter.convertTicketEntityToDTO(getTicketByID(id));
    }

    private TicketEntity getTicketByID(Integer id)
    {
        TicketEntity optional = ticketRepository.findByTicketID(id);
        return optional;//on("Shelter with id " + id + " not found"));
    }


    public TicketEntity createTicket(TicketEntity ticket) {
        return ticketRepository.save(ticket);
    }



}
