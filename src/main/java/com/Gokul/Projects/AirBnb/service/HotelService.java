package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.HotelDto;
import com.Gokul.Projects.AirBnb.dto.HotelInfoDto;
import com.Gokul.Projects.AirBnb.entity.Hotel;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotel);

    void activateHotel(Long id);

    HotelInfoDto getHotelInfoById(Long hotelId);
}
