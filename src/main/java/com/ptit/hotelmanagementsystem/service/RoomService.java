package com.ptit.hotelmanagementsystem.service;

import com.ptit.hotelmanagementsystem.dto.CreateRoomRequest;
import com.ptit.hotelmanagementsystem.dto.RoomDto;
import com.ptit.hotelmanagementsystem.dto.UpdateRoomRequest;
import com.ptit.hotelmanagementsystem.model.Hotel;
import com.ptit.hotelmanagementsystem.model.Room;
import com.ptit.hotelmanagementsystem.repository.HotelRepository;
import com.ptit.hotelmanagementsystem.repository.RoomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    public RoomDto createRoom(CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + request.getHotelId()));

        Room room = new Room();
        BeanUtils.copyProperties(request, room);
        room.setHotel(hotel);
        Room savedRoom = roomRepository.save(room);
        return convertToDto(savedRoom);
    }

    public Optional<RoomDto> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(this::convertToDto);
    }

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<RoomDto> updateRoom(Long id, UpdateRoomRequest request) {
        return roomRepository.findById(id)
                .map(room -> {
                    Hotel hotel = hotelRepository.findById(request.getHotelId())
                            .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + request.getHotelId()));
                    BeanUtils.copyProperties(request, room);
                    room.setId(id);
                    room.setHotel(hotel);
                    Room updatedRoom = roomRepository.save(room);
                    return convertToDto(updatedRoom);
                });
    }

    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private RoomDto convertToDto(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setRoomNumber(room.getRoomNumber());
        roomDto.setType(room.getType());
        roomDto.setPrice(room.getPrice());
        roomDto.setAvailable(room.isAvailable());
        roomDto.setHotelId(room.getHotel().getId());
        return roomDto;
    }
}
