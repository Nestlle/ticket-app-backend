package com.ticketApp.ticketApp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Setter
public class UserTicketPK implements Serializable {

    @Column(name="id_user")
    private Integer userID;

    @Column(name="id_ticket")
    private Integer ticketID;
}
