package com.ticketApp.ticketApp.dto;

import java.util.List;

public class CheckoutEmailDTO {
    public String name;
    public List<CheckoutEmailElement> checkoutElements;
    public Double total;
}
