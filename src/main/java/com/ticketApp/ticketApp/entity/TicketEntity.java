package com.ticketApp.ticketApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "TICKET", schema = "ticket_app")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Integer ticketID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_event")
    private EventEntity event;

//    @Column(name = "id_event")
//    private Integer eventID;

    @Column(name = "price")
    private double price;

    @Column(name = "ticket_type")
    private String ticketType;

    @Column(name = "ticket_nb")
    private Integer ticketNb;

    @OneToMany(mappedBy = "user")
    private Set<CartEntity> cart = new HashSet<CartEntity>();

}
