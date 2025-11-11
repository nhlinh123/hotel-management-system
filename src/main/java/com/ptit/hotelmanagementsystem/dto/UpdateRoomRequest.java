package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class UpdateRoomRequest {
    private String roomNumber;
    private String type;
    private double price;
    private boolean available;
    private Long hotelId;
}
