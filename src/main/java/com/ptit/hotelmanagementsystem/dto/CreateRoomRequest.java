package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class CreateRoomRequest {
    private String roomNumber;
    private String type;
    private double price;
    private boolean available;
    private Long hotelId;
}
