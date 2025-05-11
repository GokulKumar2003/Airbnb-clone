package com.Gokul.Projects.AirBnb.dto;

import com.Gokul.Projects.AirBnb.entity.User;
import com.Gokul.Projects.AirBnb.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
