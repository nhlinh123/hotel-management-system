package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class AssetDto {
    private Long id;
    private String name;
    private String description;
    private String type;
    private Long roomId;
}
