package com.ticketApp.ticketApp.controller;

import com.ticketApp.ticketApp.dto.AddToCartDTO;
import com.ticketApp.ticketApp.dto.SavedReservationDTO;
import com.ticketApp.ticketApp.dto.ViewReservationDTO;
import com.ticketApp.ticketApp.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{userID}")
    private List<ViewReservationDTO> getReservationsByUserID(@PathVariable("userID") Integer userID){
        return reservationService.viewReservations(userID);
    }

    @PostMapping()
    public void saveReservation(@RequestBody SavedReservationDTO savedReservationDTO) throws Exception {
        reservationService.addReservation(savedReservationDTO);
    }

    @PostMapping("/copyToCart/{userId}")
    public void copyToCart(@RequestBody ViewReservationDTO reservationDTO, @PathVariable Integer userId){
        reservationService.copyToCart(reservationDTO, userId);
    }

    @GetMapping("/{userID}/{ticketID}")
    public void deleteReservation(@PathVariable Integer userID, @PathVariable Integer ticketID) {
        reservationService.deleteReservation(userID, ticketID);
    }
}
