package com.Gokul.Projects.AirBnb.exception;

import com.Gokul.Projects.AirBnb.dto.HotelDto;

public class HotelAlreadyActiveException extends RuntimeException {

    public HotelAlreadyActiveException(String msg){
        super(msg);
    }
}
