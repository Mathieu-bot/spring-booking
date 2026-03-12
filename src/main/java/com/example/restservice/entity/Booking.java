package com.example.restservice.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Booking {
    private final String customerName;
    private final String phoneNumber;
    private final String email;
    private final int roomNumber;
    private final String roomDescription;
    private final LocalDate reservationDate;

    public Booking(String customerName, String phoneNumber, String email, int roomNumber, String roomDescription, LocalDate reservationDate) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.reservationDate = reservationDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return roomNumber == booking.roomNumber && Objects.equals(reservationDate, booking.reservationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, reservationDate);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", roomNumber=" + roomNumber +
                ", roomDescription='" + roomDescription + '\'' +
                ", reservationDate=" + reservationDate +
                '}';
    }
}