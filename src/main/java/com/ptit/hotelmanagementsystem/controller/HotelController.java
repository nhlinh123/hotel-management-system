package com.ptit.hotelmanagementsystem.controller;

import com.ptit.hotelmanagementsystem.dto.CreateHotelRequest;
import com.ptit.hotelmanagementsystem.dto.HotelDto;
import com.ptit.hotelmanagementsystem.dto.UpdateHotelRequest;
import com.ptit.hotelmanagementsystem.dto.BaseResponseModel;
import com.ptit.hotelmanagementsystem.service.HotelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@Tag(name = "Hotel Management")
@SecurityRequirement(name = "Bearer Authentication")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<BaseResponseModel<HotelDto>> createHotel(@RequestBody CreateHotelRequest request) {
        HotelDto createdHotel = hotelService.createHotel(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseModel.created(createdHotel, "Hotel created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel<HotelDto>> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(hotelDto -> ResponseEntity.ok(BaseResponseModel.success(hotelDto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("Hotel not found")));
    }

    @GetMapping
    public ResponseEntity<BaseResponseModel<List<HotelDto>>> getAllHotels() {
        List<HotelDto> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(BaseResponseModel.success(hotels));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel<HotelDto>> updateHotel(@PathVariable Long id, @RequestBody UpdateHotelRequest request) {
        return hotelService.updateHotel(id, request)
                .map(hotelDto -> ResponseEntity.ok(BaseResponseModel.success(hotelDto, "Hotel updated successfully")))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponseModel.notFound("Hotel not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel<String>> deleteHotel(@PathVariable Long id) {
        if (hotelService.deleteHotel(id)) {
            return ResponseEntity.ok(BaseResponseModel.success("Hotel deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponseModel.notFound("Hotel not found"));
    }
}
