package com.Gokul.Projects.AirBnb.repository;

import com.Gokul.Projects.AirBnb.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
