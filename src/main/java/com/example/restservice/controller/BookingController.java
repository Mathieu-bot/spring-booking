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
    public ResponseEntity<?> createBookings(@RequestBody List<Booking> requests) {
        List<Booking> createdBookings = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (Booking request : requests) {
            int roomNumber = request.getRoomNumber();
            LocalDate reservationDate = request.getReservationDate();

            if (roomNumber < 1 | roomNumber > 9) {
                errors.add("Error: Invalid room number for " + request.getCustomerName());
                continue;
            }

            boolean isRoomBooked = bookings.stream()
                    .anyMatch(booking -> booking.getRoomNumber() == roomNumber &&
                            booking.getReservationDate().equals(reservationDate));

            if (isRoomBooked) {
                errors.add("Error: Room " + roomNumber + " is already booked for date " + reservationDate);
                continue;
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
            createdBookings.add(newBooking);
        }

        if (!errors.isEmpty() && createdBookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.ok(java.util.Map.of(
                    "created", createdBookings,
                    "errors", errors
            ));
        }

        return ResponseEntity.ok(createdBookings);
    }
}