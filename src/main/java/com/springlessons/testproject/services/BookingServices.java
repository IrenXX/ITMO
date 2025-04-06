package com.springlessons.testproject.services;

import com.springlessons.testproject.dto.BookingRequestDTO;
import com.springlessons.testproject.dto.BookingResponseDTO;
import com.springlessons.testproject.exception.InvalidDiscountException;
import com.springlessons.testproject.exception.BookingException;
import com.springlessons.testproject.exception.ServiceNotFoundException;
import com.springlessons.testproject.mapper.BookingMapper;
import com.springlessons.testproject.model.Booking;
import com.springlessons.testproject.model.Users;
import com.springlessons.testproject.repositories.BookingRepository;
import com.springlessons.testproject.repositories.UsersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServices {

    private final BookingRepository bookingRepository;
    private final BookingMapper mapper;
    private final UsersRepository usersRepository;

    public BookingResponseDTO update(@Valid BookingRequestDTO bookingRequestDTO) {
        Booking booking = mapper.mapToEntity(bookingRequestDTO);
        bookingRepository.save(booking);
        return mapper.mapToDTO(booking);
    }

    public BookingResponseDTO getById(@Valid Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(
                        () -> new BookingException("Order not found", HttpStatus.NOT_FOUND));
        return mapper.mapToDTO(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponseDTO> findAllOrders() {
        List<Booking> bookingList = bookingRepository.findAll();
        if (bookingList.isEmpty()) {throw new BookingException("Data is empty", HttpStatus.NOT_FOUND);}
        return mapper.mapFormEnityListtoDTOList(bookingList);
    }

    public Long createdOrder(@Valid BookingRequestDTO bookingRequestDTO) {
        Booking booking = mapper.mapToEntity(bookingRequestDTO);
        bookingRepository.save(booking);
        return booking.getId();
    }

    public void cancelOrder(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Map<String, ? extends Serializable>> findByBookingTimeBetween(LocalDateTime startDate, LocalDateTime endDate){
        List<Booking> bookings = bookingRepository.findAllByBookingTimeBetween(startDate, endDate);
        Map<LocalDateTime, Double> revenueByDate = bookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getBookingTime().toLocalDate().atStartOfDay(),
                        Collectors.summingDouble(Booking::getPrice)
                ));

        return revenueByDate.entrySet().stream()
                .map(entry -> Map.of(
                        "date", entry.getKey().toLocalDate().toString(),
                        "revenue", entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    public List<BookingResponseDTO> getByTime(String orderCreate) {
        try {
            LocalDateTime time = LocalDateTime.parse(orderCreate);
            List<Booking> bookings = bookingRepository.getByBookingTime(time);
            if (bookings.isEmpty()) {
                throw new ServiceNotFoundException("No bookings found for the specified time");
            }
           return mapper.mapFormEnityListtoDTOList(bookings);
        } catch (ServiceNotFoundException e) {
            throw new BookingException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public void updateDiscountForUserFutureBookings (Long userId,double discount) throws ServiceNotFoundException {
        if (discount < 0 || discount > 1) {
            throw new InvalidDiscountException("Discount must be between 0 and 1", HttpStatus.BAD_REQUEST);
        }

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ServiceNotFoundException("User not found with id: " + userId));

        user.setDiscount(discount);
        usersRepository.save(user);
        bookingRepository.updateDiscountForUserFutureBookings(userId, discount, LocalDateTime.now());
    }

    public Booking updatedBooking(Long id, Booking bookingDetails) throws ServiceNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Booking not found with id: " + id));

        booking.setBookingTime(bookingDetails.getBookingTime());
        booking.setDiscount(bookingDetails.getDiscount());
        booking.setServiceType(bookingDetails.getServiceType());
        booking.setPrice(bookingDetails.getPrice());
        return bookingRepository.save(booking);
    }

    public Users createOrUpdateOperator(Users operator) {
        if (!"Оператор".equals(operator.getUserRule())) {
            operator.setUserRule("Оператор");
        }

        return usersRepository.save(operator);
    }
}
