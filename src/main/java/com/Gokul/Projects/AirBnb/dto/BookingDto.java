package com.Gokul.Projects.AirBnb.dto;

import com.Gokul.Projects.AirBnb.entity.Guest;
import com.Gokul.Projects.AirBnb.entity.Hotel;
import com.Gokul.Projects.AirBnb.entity.Room;
import com.Gokul.Projects.AirBnb.entity.User;
import com.Gokul.Projects.AirBnb.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCnt;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
