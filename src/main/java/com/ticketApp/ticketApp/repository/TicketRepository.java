package com.ticketApp.ticketApp.repository;

import com.ticketApp.ticketApp.entity.EventEntity;
import com.ticketApp.ticketApp.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    TicketEntity findByTicketID(Integer id);

    List<TicketEntity> findByEvent(EventEntity event);

//      TicketEntity findByEvent(EventEntity event);



//    List<TicketEntity> getByEventID(Integer eventID);
}
