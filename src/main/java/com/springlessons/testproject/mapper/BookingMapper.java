package com.springlessons.testproject.mapper;

import com.springlessons.testproject.dto.BookingRequestDTO;
import com.springlessons.testproject.dto.BookingResponseDTO;
import com.springlessons.testproject.model.Booking;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingMapper {
    public BookingResponseDTO mapToDTO(Booking booking) {
        return new BookingResponseDTO(
                booking.getId(),
                booking.getServiceType(),
                booking.getPrice()
        );
    }

    public Booking mapToEntity(@Valid BookingRequestDTO bookingRequestDTO) {
        return Booking.builder()
                .price(bookingRequestDTO.price())
                .serviceType(bookingRequestDTO.name())
                .build();
    }

    public List<BookingResponseDTO> mapFormEnityListtoDTOList(List<Booking> bookingList) {
        List<BookingResponseDTO> dtoList = new ArrayList<>();
        for (Booking booking : bookingList) {
            BookingResponseDTO responseDTO = new BookingResponseDTO(
                    booking.getId(), booking.getServiceType(), booking.getPrice()
            );
            dtoList.add(responseDTO);
        }
        return dtoList;
    }
}
