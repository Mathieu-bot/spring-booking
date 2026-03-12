package com.example.restservice.dao;

import com.example.restservice.entity.Booking;

import java.util.List;

public interface BookingDAO {
    Booking save(Booking booking);
    List<Booking> findAll();
    boolean existsByRoomAndDate(int roomNumber, java.time.LocalDate reservationDate);
}