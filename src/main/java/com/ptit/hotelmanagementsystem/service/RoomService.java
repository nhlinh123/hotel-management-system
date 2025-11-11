package com.ptit.hotelmanagementsystem.service;

import com.ptit.hotelmanagementsystem.dto.RoomDto;
import com.ptit.hotelmanagementsystem.model.Room;
import com.ptit.hotelmanagementsystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RoomDto convertToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomNumber(room.getRoomNumber());
        roomDto.setType(room.getType());
        roomDto.setPrice(room.getPrice());
        roomDto.setAvailable(room.isAvailable());
        return roomDto;
    }
}
