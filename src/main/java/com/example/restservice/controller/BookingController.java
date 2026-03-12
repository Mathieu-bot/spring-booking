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
    public ResponseEntity<?> createBooking(@RequestBody Booking request) {
        int roomNumber = request.getRoomNumber();
        LocalDate reservationDate = request.getReservationDate();

        if (roomNumber < 1 || roomNumber > 9) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: Invalid room number");
        }

        if (bookingDAO.existsByRoomAndDate(roomNumber, reservationDate)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: Room " + roomNumber + " is already booked for date " + reservationDate);
        }

        Booking newBooking = bookingDAO.save(request);
        return ResponseEntity.ok(bookingDAO.findAll());
    }
}