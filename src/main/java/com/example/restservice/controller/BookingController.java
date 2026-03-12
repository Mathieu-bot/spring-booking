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
        int roomNumber = request.getRoomNumber();
        LocalDate reservationDate = request.getReservationDate();

        boolean isRoomBooked = bookings.stream()
                .anyMatch(booking -> booking.getRoomNumber() == roomNumber &&
                        booking.getReservationDate().equals(reservationDate));

        if (isRoomBooked) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Room " + roomNumber + " is already booked for date " + reservationDate);
        }

        Booking newBooking = new Booking(
                request.getCustomerName(),
                request.getPhoneNumber(),
                request.getEmail(),
                roomNumber,
                request.getRoomDescription(),
                reservationDate
        );

        bookings.add(newBooking);
        return ResponseEntity.ok(bookings);
    }
}