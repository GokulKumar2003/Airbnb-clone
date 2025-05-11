package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.RoomDto;
import com.Gokul.Projects.AirBnb.entity.Hotel;
import com.Gokul.Projects.AirBnb.entity.Room;
import com.Gokul.Projects.AirBnb.exception.ResourceNotFoundException;
import com.Gokul.Projects.AirBnb.repository.HotelRepository;
import com.Gokul.Projects.AirBnb.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final HotelRepository hotelRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final InventoryService inventoryService;

    @Override
    @Transactional
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel "+hotelId);
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel with " +
                        "id " + hotelId  + " is not found"));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if(hotel.getIsActive()){
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getRoomsAllRoomsInHotel(Long hotelId) {
        log.info("Fetching all rooms from hotel with id { " + hotelId + " }");
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel with " +
                        "id " + hotelId  + " is not found"));

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Fetching room with id " + roomId);
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room with id" +
                        " " + roomId + " is not found."));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        log.info("Deleting room with id "+roomId);
        Room room = roomRepository.findById(roomId)
                        .orElseThrow(()-> new ResourceNotFoundException("Room" +
                                " not found id: " + roomId));
        inventoryService.deleteInventory(room);
        roomRepository.deleteById(roomId);
    }
}
