package com.Gokul.Projects.AirBnb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelInfoDto {
    private HotelDto hotelDto;
    private List<RoomDto> roomDtoList;
}
