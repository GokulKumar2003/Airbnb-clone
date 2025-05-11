package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getRoomsAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);
}
