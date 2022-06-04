package com.ticketApp.ticketApp.repository;

import com.ticketApp.ticketApp.entity.CartEntity;
import com.ticketApp.ticketApp.entity.ReservationEntity;
import com.ticketApp.ticketApp.entity.UserTicketPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UserTicketPK> {

    @Query(value = " select r.id_user, r.id_ticket, r.nb_reserved_tickets, e.id_event, r.creation_date " +
            " from ticket_app.reservation r  " +
            " inner join ticket_app.ticket t on r.id_ticket=t.id_ticket " +
            " inner join ticket_app.event e on t.id_event=e.id_event " +
            " where r.id_user = :userID ",  nativeQuery = true)
    List<ReservationEntity> getReservationsByUserID(final Integer userID);

    @Query(value = " select r.id_user, r.id_ticket, r.nb_reserved_tickets, r.creation_date " +
            " from ticket_app.reservation r  " +
            " where r.id_user = :userID and r.id_ticket = :ticketID",  nativeQuery = true)
    ReservationEntity getReservationByUserIDandTicketID(Integer userID, Integer ticketID);


}
