package com.ticketApp.ticketApp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "RESERVATION", schema = "ticket_app")
public class ReservationEntity implements Serializable {

    @EmbeddedId
    private UserTicketPK id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @ManyToOne
    @MapsId("ticketID")
    @JoinColumn(name = "id_ticket")
    private TicketEntity ticket;

    @Column(name = "nb_reserved_tickets")
    private Integer numberReservedTickets;

    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now();
}
