package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.HotelDto;
import com.Gokul.Projects.AirBnb.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InventoryService {

    public void initializeRoomForAYear(Room room);

    public void deleteInventory(Room room);

    public Page<HotelDto> searchHotels(Integer page,
                                       Integer size,
                                       String city,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Integer roomCnt);
}
