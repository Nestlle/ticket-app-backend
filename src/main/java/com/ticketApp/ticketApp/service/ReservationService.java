package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.AddToCartDTO;
import com.ticketApp.ticketApp.dto.SavedReservationDTO;
import com.ticketApp.ticketApp.dto.TicketDTO;
import com.ticketApp.ticketApp.dto.ViewReservationDTO;
import com.ticketApp.ticketApp.entity.*;
import com.ticketApp.ticketApp.repository.EventRepository;
import com.ticketApp.ticketApp.repository.ReservationRepository;
import com.ticketApp.ticketApp.repository.TicketRepository;
import com.ticketApp.ticketApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private ReservationRepository reservationRepository;


    public List<ViewReservationDTO> viewReservations(Integer userID) {
        List<ReservationEntity> reservationEntities = new ArrayList<>();
        reservationEntities = reservationRepository.getReservationsByUserID(userID);

        List<ViewReservationDTO> viewReservationDTOs = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities) {
            TicketEntity ticket = new TicketEntity();
            EventEntity eventEntity = new EventEntity();
            ViewReservationDTO viewReservationDTO = new ViewReservationDTO();
            ticket = ticketRepository.findByTicketID(reservationEntity.getTicket().getTicketID());
            eventEntity = eventRepository.getById(ticket.getEvent().getEventID());
            viewReservationDTO.setEventName(eventEntity.getEventName());
            viewReservationDTO.setEventID(eventEntity.getEventID());
            viewReservationDTO.setTicketID(ticket.getTicketID());
            viewReservationDTO.setTicketType(ticket.getTicketType());
            viewReservationDTO.setNumberReservedTickets(reservationEntity.getNumberReservedTickets());
            viewReservationDTO.setPhoto(eventEntity.getPhoto());
            viewReservationDTO.setTicketPrice(ticket.getPrice());
            viewReservationDTO.setEventDate(eventEntity.getDate());
            viewReservationDTO.setEventTime(eventEntity.getStartTime());
            viewReservationDTOs.add(viewReservationDTO);
        }

        return viewReservationDTOs;

    }

    public void addReservation(SavedReservationDTO savedReservationDTO) {
        for (TicketDTO ticket : savedReservationDTO.getReservedTickets()) {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setUser(userRepository.findByUserID(savedReservationDTO.getUserId()));
//            reservationEntity = cartRepository.getCartItemByUserIDandTicketID(userRepository.findByUserID(savedReservationDTO.getUserId()).getUserID(), ticket.getTicketID());


            TicketEntity ticketEnt = ticketRepository.findByTicketID(ticket.getTicketID());
            reservationEntity.setTicket(ticketEnt);

            Integer nbExistingTickets = 0;

            if (ticket.getTicketCounter() > 0) {
                ReservationEntity existingReservationEntity = reservationRepository.getReservationByUserIDandTicketID(userRepository.findByUserID(savedReservationDTO.getUserId()).getUserID(), ticket.getTicketID());
                if(existingReservationEntity != null){
                    nbExistingTickets = existingReservationEntity.getNumberReservedTickets() + ticket.getTicketCounter();
                }
                else{
                    nbExistingTickets = ticket.getTicketCounter();
                }
//                nbTickets = ticket.getTicketCounter();
                reservationEntity.setNumberReservedTickets(nbExistingTickets);
            } else {
                reservationEntity.setNumberReservedTickets(ticket.getTicketCounter());
            }

//            EventEntity event = eventRepository.getEventByTicketID(ticketEnt.getTicketID());
//            reservationEntity.setEventID(event.getEventID());

            UserTicketPK pk = new UserTicketPK();
            pk.setUserID(savedReservationDTO.getUserId());
            pk.setTicketID(ticket.getTicketID());
            reservationEntity.setId(pk);

            reservationRepository.save(reservationEntity);

        }
    }

}
