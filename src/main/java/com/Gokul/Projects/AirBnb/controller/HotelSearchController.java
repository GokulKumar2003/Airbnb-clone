package com.Gokul.Projects.AirBnb.controller;

import com.Gokul.Projects.AirBnb.dto.HotelDto;
import com.Gokul.Projects.AirBnb.dto.HotelInfoDto;
import com.Gokul.Projects.AirBnb.service.HotelService;
import com.Gokul.Projects.AirBnb.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("hotels")
@RequiredArgsConstructor
public class HotelSearchController {

    @Autowired
    private final InventoryService inventoryService;
    @Autowired
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotel(@RequestParam String city,
                                                      @RequestParam LocalDate startDate,
                                                      @RequestParam LocalDate endDate,
                                                      @RequestParam Integer roomCnt,
                                                      @RequestParam Integer page,
                                                      @RequestParam Integer size){
        System.out.println(city + " " + startDate + " " + endDate + " " + roomCnt + " " + page + " " + size);
        System.out.println("Hello");
        Page<HotelDto> hotelDtoPage = inventoryService.searchHotels(page, size,
                city, startDate,
                endDate, roomCnt);
        return new ResponseEntity<>(hotelDtoPage, HttpStatus.FOUND);

    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
