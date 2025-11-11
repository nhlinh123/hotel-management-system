package com.ptit.hotelmanagementsystem.service;

import com.ptit.hotelmanagementsystem.dto.CreateHotelRequest;
import com.ptit.hotelmanagementsystem.dto.HotelDto;
import com.ptit.hotelmanagementsystem.dto.UpdateHotelRequest;
import com.ptit.hotelmanagementsystem.model.Hotel;
import com.ptit.hotelmanagementsystem.repository.HotelRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public HotelDto createHotel(CreateHotelRequest request) {
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(request, hotel);
        Hotel savedHotel = hotelRepository.save(hotel);
        HotelDto hotelDto = new HotelDto();
        BeanUtils.copyProperties(savedHotel, hotelDto);
        return hotelDto;
    }

    public Optional<HotelDto> getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    HotelDto hotelDto = new HotelDto();
                    BeanUtils.copyProperties(hotel, hotelDto);
                    return hotelDto;
                });
    }

    public List<HotelDto> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotel -> {
                    HotelDto hotelDto = new HotelDto();
                    BeanUtils.copyProperties(hotel, hotelDto);
                    return hotelDto;
                })
                .collect(Collectors.toList());
    }

    public Optional<HotelDto> updateHotel(Long id, UpdateHotelRequest request) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    BeanUtils.copyProperties(request, hotel);
                    hotel.setId(id); // Ensure the ID remains the same
                    Hotel updatedHotel = hotelRepository.save(hotel);
                    HotelDto hotelDto = new HotelDto();
                    BeanUtils.copyProperties(updatedHotel, hotelDto);
                    return hotelDto;
                });
    }

    public boolean deleteHotel(Long id) {
        if (hotelRepository.existsById(id)) {
            hotelRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
