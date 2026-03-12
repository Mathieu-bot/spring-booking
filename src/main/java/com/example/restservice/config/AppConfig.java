package com.example.restservice.config;

import com.example.restservice.dao.BookingDAO;
import com.example.restservice.dao.impl.BookingDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public BookingDAO bookingDAO() {
        return new BookingDAOImpl();
    }
}