package com.Gokul.Projects.AirBnb.controller;

import com.Gokul.Projects.AirBnb.dto.HotelDto;
import com.Gokul.Projects.AirBnb.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    @Autowired
    private final HotelService hotelService;

    /* Create a new hotel */
    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){

        HotelDto hotel = hotelService.createNewHotel(hotelDto);
        return new ResponseEntity<>(hotel, HttpStatus.CREATED);
    }

    /* Get a hotel By id */
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long hotelId){
        HotelDto hotelDto = hotelService.getHotelById(hotelId);
        return ResponseEntity.ok(hotelDto);
    }

    /* Update a hotel info */
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long hotelId,
                                                    @RequestBody HotelDto hotelDto){
        HotelDto hotelDto1 = hotelService.updateHotelById(hotelId,
                hotelDto);

        return ResponseEntity.ok(hotelDto1);
    }

    @PatchMapping("/{hotelId}")
    public void activateHotel(@PathVariable Long hotelId){
        hotelService.activateHotel(hotelId);
        return;
    }

}
