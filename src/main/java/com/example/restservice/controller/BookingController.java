package com.example.restservice.controller;

import com.example.restservice.dao.BookingDAO;
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

    private final BookingDAO bookingDAO;

    public BookingController(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @GetMapping("/booking")
    public List<Booking> getBookings() {
        return bookingDAO.findAll();
    }

    @PostMapping("/booking")
    public ResponseEntity<?> createBooking(@RequestBody List<Booking> requests) {
        List<String> errors = new ArrayList<>();

        for (Booking request : requests) {
            int roomNumber = request.getRoomNumber();
            LocalDate reservationDate = request.getReservationDate();

            if (roomNumber < 1 || roomNumber > 9) {
                errors.add("Error: Invalid room number " + roomNumber);
                continue;
            }

            if (bookingDAO.existsByRoomAndDate(roomNumber, reservationDate)) {
                errors.add("Error: Room " + roomNumber + " is already booked for date " + reservationDate);
                continue;
            }

            bookingDAO.save(request);
        }

        if (!errors.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errors);
        }

        return ResponseEntity.ok(bookingDAO.findAll());
    }
}