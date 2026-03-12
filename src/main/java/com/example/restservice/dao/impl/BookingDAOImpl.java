package com.example.restservice.dao.impl;

import com.example.restservice.config.DBConnection;
import com.example.restservice.dao.BookingDAO;
import com.example.restservice.entity.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private final DBConnection dbConnection;

    public BookingDAOImpl() {
        this.dbConnection = new DBConnection();
    }

    @Override
    public Booking save(Booking booking) {
        String sql = """
            INSERT INTO bookings (customer_name, phone_number, email, room_number, room_description, reservation_date)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id, customer_name, phone_number, email, room_number, room_description, reservation_date
            """;

        Connection conn = null;
        try {
            conn = dbConnection.getDBConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, booking.getCustomerName());
            ps.setString(2, booking.getPhoneNumber());
            ps.setString(3, booking.getEmail());
            ps.setInt(4, booking.getRoomNumber());
            ps.setString(5, booking.getRoomDescription());
            ps.setDate(6, Date.valueOf(booking.getReservationDate()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToBooking(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save booking", e);
        } finally {
            dbConnection.close(conn);
        }

        throw new RuntimeException("Failed to save booking - no result returned");
    }

    @Override
    public List<Booking> findAll() {
        return List.of();
    }

    @Override
    public boolean existsByRoomAndDate(int roomNumber, LocalDate reservationDate) {
        return false;
    }

    private Booking mapRowToBooking(ResultSet rs) throws SQLException {
        return new Booking(
            rs.getString("customer_name"),
            rs.getString("phone_number"),
            rs.getString("email"),
            rs.getInt("room_number"),
            rs.getString("room_description"),
            rs.getDate("reservation_date").toLocalDate()
        );
    }
}