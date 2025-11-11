package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.CreateRoomRequest;
import com.ptit.hotelmanagementsystem.dto.RoomDto;
import com.ptit.hotelmanagementsystem.dto.UpdateRoomRequest;
import com.ptit.hotelmanagementsystem.dto.BaseResponseModel;
import com.ptit.hotelmanagementsystem.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Room Management")
@SecurityRequirement(name = "Bearer Authentication")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<BaseResponseModel<RoomDto>> createRoom(@RequestBody CreateRoomRequest request) {
        RoomDto createdRoom = roomService.createRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseModel.created(createdRoom, "Room created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel<RoomDto>> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(roomDto -> ResponseEntity.ok(BaseResponseModel.success(roomDto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("Room not found")));
    }

    @GetMapping
    public ResponseEntity<BaseResponseModel<List<RoomDto>>> getAllRooms() {
        List<RoomDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(BaseResponseModel.success(rooms));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel<RoomDto>> updateRoom(@PathVariable Long id, @RequestBody UpdateRoomRequest request) {
        return roomService.updateRoom(id, request)
                .map(roomDto -> ResponseEntity.ok(BaseResponseModel.success(roomDto, "Room updated successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("Room not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel<String>> deleteRoom(@PathVariable Long id) {
        if (roomService.deleteRoom(id)) {
            return ResponseEntity.ok(BaseResponseModel.success("Room deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponseModel.notFound("Room not found"));
    }
}
