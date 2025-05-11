package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.HotelDto;
import com.Gokul.Projects.AirBnb.entity.Hotel;
import com.Gokul.Projects.AirBnb.entity.Inventory;
import com.Gokul.Projects.AirBnb.entity.Room;
import com.Gokul.Projects.AirBnb.repository.InventoryRepository;
import com.Gokul.Projects.AirBnb.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        for(; today.isBefore(endDate); today = today.plusDays(1)){
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCnt(0)
                    .reservedCnt(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCnt(room.getTotalCnt())
                    .close(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteInventory(Room room){
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelDto> searchHotels(Integer page,
                                       Integer size,
                                       String city,
                                       LocalDate startDate,
                                       LocalDate endDate,
                                       Integer roomCnt) {

        Pageable pageable = PageRequest.of(page, size);
        Long dateCnt = ChronoUnit.DAYS.between(startDate, endDate);

        Page<Hotel> hotels = inventoryRepository.searchHotels(city, startDate
                , endDate, roomCnt, dateCnt+1,pageable);

        return hotels.map((element) -> modelMapper.map(element,
                HotelDto.class));
    }
}
