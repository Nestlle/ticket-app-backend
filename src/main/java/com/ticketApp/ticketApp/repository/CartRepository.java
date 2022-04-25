package com.ticketApp.ticketApp.repository;

import com.ticketApp.ticketApp.entity.CartEntity;
import com.ticketApp.ticketApp.entity.UserTicketPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UserTicketPK> {

    @Query(value = " select c.id_user, c.id_ticket, c.nb_cart_tickets, e.id_event " +
            " from ticket_app.cart c  " +
            " inner join ticket_app.ticket t on c.id_ticket=t.id_ticket " +
            " inner join ticket_app.event e on t.id_event=e.id_event " +
            " where c.id_user = :userID ",  nativeQuery = true)
    List<CartEntity> getCartByUserID(final Integer userID);

    @Query(value = " select c.id_user, c.id_ticket, c.nb_cart_tickets " +
            " from ticket_app.cart c  " +
            " where c.id_user = :userID and c.id_ticket = :ticketID",  nativeQuery = true)
    CartEntity getCartItemByUserIDandTicketID(Integer userID, Integer ticketID);
}
