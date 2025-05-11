package com.Gokul.Projects.AirBnb.controller;

import com.Gokul.Projects.AirBnb.dto.BookingDto;
import com.Gokul.Projects.AirBnb.dto.BookingRequest;
import com.Gokul.Projects.AirBnb.dto.GuestDto;
import com.Gokul.Projects.AirBnb.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    @Autowired
    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                @RequestBody List<GuestDto> guests){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guests));
    }
}
