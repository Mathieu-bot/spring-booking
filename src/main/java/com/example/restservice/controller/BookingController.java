package com.example.restservice.controller;

import com.example.restservice.entity.Booking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {

    private final List<Booking> bookings = new ArrayList<>();

    @GetMapping("/booking")
    public List<Booking> getBookings() {
        return bookings;
    }

    @PostMapping("/booking")
    public ResponseEntity<?> createBooking(@RequestBody Booking request) {

        Booking newBooking = new Booking(
                request.getCustomerName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getRoomNumber(),
                request.getRoomDescription(),
                request.getReservationDate()
        );

        bookings.add(newBooking);
        return ResponseEntity.ok(bookings);
    }
}