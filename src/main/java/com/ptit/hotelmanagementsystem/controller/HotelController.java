package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.CreateHotelRequest;
import com.ptit.hotelmanagementsystem.dto.HotelDto;
import com.ptit.hotelmanagementsystem.dto.UpdateHotelRequest;
import com.ptit.hotelmanagementsystem.service.HotelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@Tag(name = "Hotel Management")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createHotel(@RequestBody CreateHotelRequest request) {
        HotelDto createdHotel = hotelService.createHotel(request);
        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(hotelDto -> new ResponseEntity<>(hotelDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        List<HotelDto> hotels = hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable Long id, @RequestBody UpdateHotelRequest request) {
        return hotelService.updateHotel(id, request)
                .map(hotelDto -> new ResponseEntity<>(hotelDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        if (hotelService.deleteHotel(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
