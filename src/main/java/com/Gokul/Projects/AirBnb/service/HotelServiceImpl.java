package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.HotelDto;
import com.Gokul.Projects.AirBnb.dto.HotelInfoDto;
import com.Gokul.Projects.AirBnb.dto.RoomDto;
import com.Gokul.Projects.AirBnb.entity.Hotel;
import com.Gokul.Projects.AirBnb.entity.Room;
import com.Gokul.Projects.AirBnb.exception.HotelAlreadyActiveException;
import com.Gokul.Projects.AirBnb.exception.ResourceNotFoundException;
import com.Gokul.Projects.AirBnb.repository.HotelRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    @Autowired
    private final HotelRepository hotelRepository;
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel: { "+ hotelDto.getName()+" }");

        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setIsActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel: { "+ hotelDto.getName()+" } with ID: " + hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Fetching the hotel with ID: "+id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found. Id:" +
                        " "+id));

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with id " + id);
        Hotel hotel =
                hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found. id: "+id));

        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        hotelRepository.save(hotel);
        return hotelDto;
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        log.info("Activating the hotel with id " + id);
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel with " +
                        "id " + id + " not found"));
        if(hotel.getIsActive()){
            throw new HotelAlreadyActiveException("Hotel is already active");
        }
        hotel.setIsActive(true);

        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel with " +
                        "id " + hotelId + " not found"));

        List<RoomDto> roomDtoList = hotel.getRooms()
                .stream()
                .map((element)->modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), roomDtoList);
    }
}
