package com.springlessons.testproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.springlessons.testproject.exception.ServiceNotFoundException;
import com.springlessons.testproject.model.Booking;
import com.springlessons.testproject.model.Users;
import com.springlessons.testproject.services.BookingServices;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/bookings")
@AllArgsConstructor
public class BookingController {

    private final BookingServices bookingServices;

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking bookingDetails) {
        try {
            Booking updatedBooking = bookingServices.updatedBooking(id, bookingDetails);
            return ResponseEntity.ok(updatedBooking);
        } catch (ServiceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PatchMapping("/users/{userId}/discount")
    public ResponseEntity<?> updateUserDiscount(@PathVariable Long userId, @RequestParam double discount){
        try {
            bookingServices.updateDiscountForUserFutureBookings(userId,discount);
            return ResponseEntity.ok().build();
        } catch (ServiceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/operators")
    public ResponseEntity<Users> createOrUpdateOperator(@RequestBody Users operator) {
        Users savedOperator = bookingServices.createOrUpdateOperator(operator);
        return ResponseEntity.ok(savedOperator);
    }
} 