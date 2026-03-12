CREATE DATABASE booking_db;

\c booking_db

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    room_number INTEGER NOT NULL,
    room_description TEXT,
    reservation_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
