package com.example.restservice.controller;

import com.example.restservice.dao.BookingDAO;
import com.example.restservice.entity.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookingController bookingController; // for mock data

    @Test
    void testGetBookings_ReturnsListOfBookings() {
        Booking booking1 = new Booking("Client 1", "0123456789", "client1@example.com",
                1, "Chambre 1", LocalDate.of(2025, 9, 5));
        Booking booking2 = new Booking("Client 2", "0987654321", "client2@example.com",
                2, "Chambre 2", LocalDate.of(2025, 9, 6));

        when(bookingDAO.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> result = bookingController.getBookings();

        assertEquals(2, result.size());
        assertEquals("Client 1", result.get(0).getCustomerName());
        assertEquals("Client 2", result.get(1).getCustomerName());

        verify(bookingDAO, times(1)).findAll();
    }
}