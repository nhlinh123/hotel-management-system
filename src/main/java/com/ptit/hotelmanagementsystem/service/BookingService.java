package com.ptit.hotelmanagementsystem.service;

import com.ptit.hotelmanagementsystem.dto.BookingDto;
import com.ptit.hotelmanagementsystem.dto.CreateBookingRequest;
import com.ptit.hotelmanagementsystem.dto.UpdateBookingRequest;
import com.ptit.hotelmanagementsystem.model.Booking;
import com.ptit.hotelmanagementsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public BookingDto createBooking(CreateBookingRequest request) {
        Booking booking = Booking.builder()
                .roomId(request.getRoomId())
                .userId(request.getUserId())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .status(request.getStatus())
                .totalPrice(request.getTotalPrice())
                .guestName(request.getGuestName())
                .guestEmail(request.getGuestEmail())
                .guestPhone(request.getGuestPhone())
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        return mapToDto(savedBooking);
    }

    public Optional<BookingDto> getBookingById(Long id) {
        return bookingRepository.findById(id).map(this::mapToDto);
    }

    public List<BookingDto> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<BookingDto> updateBooking(Long id, UpdateBookingRequest request) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setRoomId(request.getRoomId());
            booking.setUserId(request.getUserId());
            booking.setCheckInDate(request.getCheckInDate());
            booking.setCheckOutDate(request.getCheckOutDate());
            booking.setStatus(request.getStatus());
            booking.setTotalPrice(request.getTotalPrice());
            booking.setGuestName(request.getGuestName());
            booking.setGuestEmail(request.getGuestEmail());
            booking.setGuestPhone(request.getGuestPhone());
            
            Booking updatedBooking = bookingRepository.save(booking);
            return mapToDto(updatedBooking);
        });
    }

    public boolean deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private BookingDto mapToDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .roomId(booking.getRoomId())
                .userId(booking.getUserId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .status(booking.getStatus())
                .totalPrice(booking.getTotalPrice())
                .guestName(booking.getGuestName())
                .guestEmail(booking.getGuestEmail())
                .guestPhone(booking.getGuestPhone())
                .build();
    }
}
