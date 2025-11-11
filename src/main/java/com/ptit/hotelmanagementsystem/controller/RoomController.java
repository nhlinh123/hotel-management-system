package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.RoomDto;
import com.ptit.hotelmanagementsystem.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }
}
