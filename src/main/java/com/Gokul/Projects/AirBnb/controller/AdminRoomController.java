package com.Gokul.Projects.AirBnb.controller;

import com.Gokul.Projects.AirBnb.dto.RoomDto;
import com.Gokul.Projects.AirBnb.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
public class AdminRoomController {

    @Autowired
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId,
                                 @RequestBody RoomDto roomDto){
        RoomDto roomDto1 = roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(roomDto1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInAHotel(@PathVariable Long hotelId){
        return ResponseEntity.ok(roomService.getRoomsAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId){
        RoomDto roomDto = roomService.getRoomById(roomId);

        return new ResponseEntity<>(roomDto, HttpStatus.FOUND);
    }

    @DeleteMapping("/{roomId}")
    public void deleteRoomById(@PathVariable Long hotelId,
                               @PathVariable Long roomId){
        roomService.deleteRoomById(roomId);
    }
}
