package com.ticketApp.ticketApp.repository;

import com.ticketApp.ticketApp.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    @Query(value = "select e.location, e.date, c.name, e.event_name, e.id_event, e.description, e.photo, e.start_time, e.category_id " +
            " from ticket_app.event e inner join ticket_app.category c on " +
            " e.category_id = c.id_category " +
            " where e.location ilike %:location% and " +
            " e.date between :from and :to and " +
            " c.name = :category",  nativeQuery = true)
    List<EventEntity> getEventsBySearchCriteria(final String location, final Date from, final Date to, final String category);

    @Query(value = "select e.id_event, e.event_name, e.location, e.description, e.date, e.photo, e.start_time " +
            " from ticket_app.event e inner join ticket_app.ticket t on " +
            " e.id_event = t.id_event " +
            " where t.id_ticket = :ticketID ",  nativeQuery = true)
    EventEntity getEventByTicketID(Integer ticketID);

    //List<EventEntity> findAllAfterToday();
}

