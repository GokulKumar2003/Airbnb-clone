package com.Gokul.Projects.AirBnb.repository;

import com.Gokul.Projects.AirBnb.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
