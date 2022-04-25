//package com.ticketApp.ticketApp.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name= "ROLE", schema = "ticket_app")
//public class RoleEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_role")
//    private Integer roleID;
//
//    @Column(name = "name")
//    private String name;
//
//
//    @JoinColumn(name = "id_user")
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private UserEntity user;
//}
