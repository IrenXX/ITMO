package com.springlessons.testproject.controllers;

import com.springlessons.testproject.dto.BookingRequestDTO;
import com.springlessons.testproject.dto.BookingResponseDTO;
import com.springlessons.testproject.exception.InvalidDateRangeException;
import com.springlessons.testproject.exception.BookingException;
import com.springlessons.testproject.services.BookingServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Validated
@RequestMapping("/api/v1/order")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BookingServicesController {

    private final BookingServices bookingServices;

    @PostMapping
    public ResponseEntity<?> createdOrder(@RequestBody @Valid BookingRequestDTO bookingRequestDTO) {
        log.info("Services created");
        URI uri = URI.create("api/v1/order/"
                + bookingServices.createdOrder(bookingRequestDTO));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public BookingResponseDTO updateService(@RequestBody @Valid BookingRequestDTO bookingRequestDTO) {
        return bookingServices.update(bookingRequestDTO);
    }

    @GetMapping(produces = "application/json")
    public List<BookingResponseDTO> findAllOrders() {
        log.info("Get all Orders");
        return bookingServices.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> findById(@PathVariable Long id) {
        log.info("Get Services from Order by ID {}", id);
        try {
            return new ResponseEntity<>(bookingServices.getById(id), HttpStatus.OK);
        } catch (BookingException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletedTechService(@RequestParam Long id) {
        log.info("Delete Service by ID {}", id);
        bookingServices.cancelOrder(id);
    }

    @GetMapping("/revenue")
    public ResponseEntity<List<Map<String, ? extends Serializable>>> getRevenueByPeriod(@RequestParam String start,
                                                                                        @RequestParam String end) {
        try {
            LocalDateTime startDate = LocalDateTime.parse(start);
            LocalDateTime endDate = LocalDateTime.parse(end);

            if (startDate.isAfter(endDate)) {
                throw new InvalidDateRangeException("Start date must be before end date", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(bookingServices.findByBookingTimeBetween(startDate, endDate), HttpStatus.OK);

        } catch (InvalidDateRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/by-time")
    public ResponseEntity<List<BookingResponseDTO>> getByTime(@RequestParam String orderCreate) {
        try {
            return new ResponseEntity<>(bookingServices.getByTime(orderCreate), HttpStatus.OK);
        } catch (BookingException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }
}
