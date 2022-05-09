package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.AddToCartDTO;
import com.ticketApp.ticketApp.dto.SavedReservationDTO;
import com.ticketApp.ticketApp.dto.TicketDTO;
import com.ticketApp.ticketApp.dto.ViewReservationDTO;
import com.ticketApp.ticketApp.entity.*;
import com.ticketApp.ticketApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CartRepository cartRepository;

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

    public void copyToCart(ViewReservationDTO reservationDTO, Integer userId) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUser(userRepository.findByUserID(userId));

        TicketEntity ticketEnt = ticketRepository.findByTicketID(reservationDTO.getTicketID());
        cartEntity.setTicket(ticketEnt);

        Integer nbExistingTickets = 0;
        if (reservationDTO.getNumberReservedTickets() > 0) {
            CartEntity existingCartEntity = cartRepository.getCartItemByUserIDandTicketID(userRepository.findByUserID(userId).getUserID(),
                    reservationDTO.getTicketID());
            if(existingCartEntity != null){
                nbExistingTickets = existingCartEntity.getNumberOfCartTickets() + reservationDTO.getNumberReservedTickets();
            }
            else{
                nbExistingTickets = reservationDTO.getNumberReservedTickets();
            }

            cartEntity.setNumberOfCartTickets(nbExistingTickets);
        } else {
            cartEntity.setNumberOfCartTickets(reservationDTO.getNumberReservedTickets());
        }


        UserTicketPK pk = new UserTicketPK();
        pk.setUserID(userId);
        pk.setTicketID(reservationDTO.getTicketID());
        cartEntity.setId(pk);

        cartRepository.save(cartEntity);

        Optional<ReservationEntity> reservationEntity = Optional.ofNullable(reservationRepository.getReservationByUserIDandTicketID(userId, reservationDTO.getTicketID()));
        if(reservationEntity.isPresent()){
            reservationRepository.delete(reservationEntity.get());
        }
    }

    public void deleteReservation(Integer userID, Integer ticketID) {
        reservationRepository.delete(reservationRepository.getReservationByUserIDandTicketID(userID, ticketID));
    }
}
