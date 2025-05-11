package com.Gokul.Projects.AirBnb.repository;

import com.Gokul.Projects.AirBnb.entity.Hotel;
import com.Gokul.Projects.AirBnb.entity.Room;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel(Hotel hotel);
}
