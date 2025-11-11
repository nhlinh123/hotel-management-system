package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class UpdateHotelRequest {
    private String name;
    private String address;
    private String city;
    private String country;
}
