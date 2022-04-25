package com.ticketApp.ticketApp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "CATEGORY", schema = "ticket_app")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Integer categoryID;

    @Column(name = "name")
    private String categoryName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private List<EventEntity> events = new ArrayList<>();
}
