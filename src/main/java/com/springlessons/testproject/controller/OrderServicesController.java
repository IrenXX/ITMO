package com.springlessons.testproject.controller;

import com.springlessons.testproject.dto.OrderRequestDTO;
import com.springlessons.testproject.dto.OrderResponseDTO;
import com.springlessons.testproject.exception.InvalidDateRangeException;
import com.springlessons.testproject.exception.OrderException;
import com.springlessons.testproject.services.OrderServices;
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
public class OrderServicesController {

    private final OrderServices orderServices;

    @PostMapping()
    public ResponseEntity<?> createdOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        log.info("Services created");
        URI uri = URI.create("api/v1/order/"
                + orderServices.createdOrder(orderRequestDTO));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO updateService(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        return orderServices.update(orderRequestDTO);
    }

    @GetMapping(produces = "application/json")
    public List<OrderResponseDTO> findAllOrders() {
        log.info("Get all Orders");
        return orderServices.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        log.info("Get Services from Order by ID {}",id);
        try {
            return new ResponseEntity<>(orderServices.getById(id), HttpStatus.OK);
        } catch (OrderException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletedTechService(@RequestParam Long id) {
        log.info("Delete Service by ID {}", id);
        orderServices.cancelOrder(id);
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
           return new ResponseEntity<>(orderServices.findByBookingTimeBetween(startDate, endDate), HttpStatus.OK);

        } catch (InvalidDateRangeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/by-time")
    public ResponseEntity<List<OrderResponseDTO>> getByTime(@RequestParam String orderCreate) {
        try {
            return new ResponseEntity<>(orderServices.getByTime(orderCreate), HttpStatus.OK);
        } catch (OrderException e) {
            throw new ResponseStatusException(e.getStatus(), e.getMessage());
        }
    }
}
