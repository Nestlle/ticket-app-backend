package com.ticketApp.ticketApp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "CART", schema = "ticket_app")
public class CartEntity implements Serializable {

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

    @Column(name = "nb_cart_tickets")
    private Integer numberOfCartTickets;

}
