package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.BookingDto;
import com.Gokul.Projects.AirBnb.dto.BookingRequest;
import com.Gokul.Projects.AirBnb.dto.GuestDto;

import java.util.List;

public interface BookingService {

    BookingDto initializeBooking(BookingRequest bookingRequest);
    BookingDto addGuests(Long bookingId, List<GuestDto> guests);
}
