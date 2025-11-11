package com.ptit.hotelmanagementsystem.dto;

import lombok.Data;

@Data
public class UpdateAssetRequest {
    private String name;
    private String description;
    private String type;
    private Long roomId;
}
