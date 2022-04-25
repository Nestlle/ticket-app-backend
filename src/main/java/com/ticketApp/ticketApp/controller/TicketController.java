package com.ticketApp.ticketApp.controller;

import com.ticketApp.ticketApp.dto.TicketDTO;
import com.ticketApp.ticketApp.entity.TicketEntity;
import com.ticketApp.ticketApp.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping()
    public List<TicketDTO> getTickets(){
        return ticketService.getTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getTicket(@PathVariable("id") Integer id) {
        return ticketService.findTicketById(id);
    }

    @PostMapping()
    public TicketEntity createTicket(@RequestBody TicketEntity ticket) {
        return ticketService.createTicket(ticket);
    }
}
