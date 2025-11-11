package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class RoomDto {

    private Long id;
    private String roomNumber;
    private String type;
    private double price;
    private boolean available;
}
