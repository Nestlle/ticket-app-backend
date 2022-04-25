package com.ticketApp.ticketApp.controller;

import com.ticketApp.ticketApp.dto.AddToCartDTO;
import com.ticketApp.ticketApp.dto.ViewCartDTO;
import com.ticketApp.ticketApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userID}")
    private List<ViewCartDTO> getCartItemsByUserID(@PathVariable("userID") Integer userID){
        return cartService.viewCartItems(userID);
    }

    @PostMapping()
    public void saveInCart(@RequestBody AddToCartDTO addedToCartDTO) throws Exception {
       cartService.addToCart(addedToCartDTO);
    }


    @GetMapping("/{userID}/{ticketID}")
    public void deleteCartItem(@PathVariable Integer userID, @PathVariable Integer ticketID) {
        cartService.deleteCartItem(userID, ticketID);
    }
}
