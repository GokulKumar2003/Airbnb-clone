package com.Gokul.Projects.AirBnb.service;

import com.Gokul.Projects.AirBnb.dto.BookingDto;
import com.Gokul.Projects.AirBnb.dto.BookingRequest;
import com.Gokul.Projects.AirBnb.dto.GuestDto;
import com.Gokul.Projects.AirBnb.entity.*;
import com.Gokul.Projects.AirBnb.entity.enums.BookingStatus;
import com.Gokul.Projects.AirBnb.exception.ResourceNotFoundException;
import com.Gokul.Projects.AirBnb.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    @Autowired
    private final BookingRepository bookingRepository;
    @Autowired
    private final HotelRepository hotelRepository;
    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final GuestRepository guestRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initializeBooking(BookingRequest bookingRequest){
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel with " +
                        "id " + bookingRequest.getHotelId() + " not found"));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()-> new ResourceNotFoundException("Room with id" +
                        " " + bookingRequest.getRoomId() + " is not found."));

        List<Inventory> inventoryList =
                inventoryRepository.findAndLockAvailableInventory(bookingRequest.getRoomId(),
                        bookingRequest.getCheckInDate(),
                        bookingRequest.getCheckOutDate(),
                        bookingRequest.getRoomCnt());

        Long daysCnt =
                ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),
                        bookingRequest.getCheckOutDate()) + 1;
        if(inventoryList.size() != daysCnt){
            throw new IllegalStateException("Room is not available");
        }

        /* reserve the rooms */
        for(Inventory inventory:inventoryList){
            inventory.setReservedCnt(inventory.getReservedCnt() + bookingRequest.getRoomCnt());
        }
        inventoryRepository.saveAll(inventoryList);

        /* Create booking */
        User user = new User();
        user.setId(1L);

        // TODO: calculate dynamic price

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .user(user)
                .roomsCnt(bookingRequest.getRoomCnt())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guests) {
        Booking booking =
                bookingRepository.findById(bookingId)
                .orElseThrow(()-> new ResourceNotFoundException("Booking with" +
                        "id " + bookingId + " not found"));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Cannot add guests now..");
        }

        for(GuestDto guestDto : guests){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        User user = new User();
        user.setId(1L);
        return user;
    }
}
