package com.ticketApp.ticketApp.service;

import com.ticketApp.ticketApp.dto.AddToCartDTO;
import com.ticketApp.ticketApp.dto.TicketDTO;
import com.ticketApp.ticketApp.dto.ViewCartDTO;
import com.ticketApp.ticketApp.entity.CartEntity;
import com.ticketApp.ticketApp.entity.EventEntity;
import com.ticketApp.ticketApp.entity.TicketEntity;
import com.ticketApp.ticketApp.entity.UserTicketPK;
import com.ticketApp.ticketApp.repository.CartRepository;
import com.ticketApp.ticketApp.repository.EventRepository;
import com.ticketApp.ticketApp.repository.TicketRepository;
import com.ticketApp.ticketApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;


    public void addToCart(AddToCartDTO addedToCartDTO) {
        for (TicketDTO ticket : addedToCartDTO.getBoughtTickets()) {
            CartEntity cartEntity = new CartEntity();
            cartEntity.setUser(userRepository.findByUserID(addedToCartDTO.getUserId()));
//            cartEntity = cartRepository.getCartItemByUserIDandTicketID(userRepository.findByUserID(addedToCartDTO.getUserId()).getUserID(), ticket.getTicketID());


            TicketEntity ticketEnt = ticketRepository.findByTicketID(ticket.getTicketID());
            cartEntity.setTicket(ticketEnt);

            Integer nbExistingTickets = 0;

            if (ticket.getTicketCounter() > 0) {
                CartEntity existingCartEntity = cartRepository.getCartItemByUserIDandTicketID(userRepository.findByUserID(addedToCartDTO.getUserId()).getUserID(), ticket.getTicketID());
                if(existingCartEntity != null){
                    nbExistingTickets = existingCartEntity.getNumberOfCartTickets() + ticket.getTicketCounter();
                }
                else{
                    nbExistingTickets = ticket.getTicketCounter();
                }
//                nbTickets = ticket.getTicketCounter();
                cartEntity.setNumberOfCartTickets(nbExistingTickets);
            } else {
                cartEntity.setNumberOfCartTickets(ticket.getTicketCounter());
            }

//            EventEntity event = eventRepository.getEventByTicketID(ticketEnt.getTicketID());
//            cartEntity.setEventID(event.getEventID());

            UserTicketPK pk = new UserTicketPK();
            pk.setUserID(addedToCartDTO.getUserId());
            pk.setTicketID(ticket.getTicketID());
            cartEntity.setId(pk);

            cartRepository.save(cartEntity);

        }
    }

    public List<ViewCartDTO> viewCartItems(Integer userID) {
        List<CartEntity> cartEntities = new ArrayList<>();
        cartEntities = cartRepository.getCartByUserID(userID);

        List<ViewCartDTO> viewCartDTOS = new ArrayList<>();
        for (CartEntity cartEntity : cartEntities) {
            TicketEntity ticket = new TicketEntity();
            EventEntity eventEntity = new EventEntity();
            ViewCartDTO viewCartDTO = new ViewCartDTO();
            ticket = ticketRepository.findByTicketID(cartEntity.getTicket().getTicketID());
            eventEntity = eventRepository.getById(ticket.getEvent().getEventID());
            viewCartDTO.setEventName(eventEntity.getEventName());
            viewCartDTO.setEventID(eventEntity.getEventID());
            viewCartDTO.setTicketID(ticket.getTicketID());
            viewCartDTO.setTicketType(ticket.getTicketType());
            viewCartDTO.setNumberCartTickets(cartEntity.getNumberOfCartTickets());
            viewCartDTO.setPhoto(eventEntity.getPhoto());
            viewCartDTO.setTicketPrice(ticket.getPrice());
            viewCartDTOS.add(viewCartDTO);
        }

        return viewCartDTOS;


    }

    public void deleteCartItem(Integer userID, Integer ticketID) {
        cartRepository.delete(cartRepository.getCartItemByUserIDandTicketID(userID, ticketID));
    }

    public void deleteCart(Integer userID) {
        List<CartEntity> entities = cartRepository.getCartByUserID(userID);
        cartRepository.deleteAll(entities);
    }

    public void substractBoughtTickets(Integer userID){
        List<CartEntity> cartEntities = new ArrayList<>();
        cartEntities = cartRepository.getCartByUserID(userID);
        for(CartEntity cartEntity : cartEntities){
            TicketEntity ticket = ticketRepository.findByTicketID(cartEntity.getTicket().getTicketID());
            Integer ticketNb = ticket.getTicketNb();
            Integer boughtTickets = cartEntity.getNumberOfCartTickets();
            Integer remainingTickets = ticketNb - boughtTickets;
            ticket.setTicketNb(remainingTickets);
        }
    }

}
