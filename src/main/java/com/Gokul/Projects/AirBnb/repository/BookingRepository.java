package com.Gokul.Projects.AirBnb.repository;

import com.Gokul.Projects.AirBnb.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
