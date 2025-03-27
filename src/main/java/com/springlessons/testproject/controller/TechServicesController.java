package com.springlessons.testproject.controller;

import com.springlessons.testproject.dto.TechRequestDTO;
import com.springlessons.testproject.dto.TechResponseDTO;
import com.springlessons.testproject.services.TechServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RequestMapping("/api/v1/tech")
@RestController
@AllArgsConstructor
@Slf4j
public class TechServicesController {

    private final TechServices techServices;

    @PostMapping()
    public ResponseEntity<?> createdTechService(@RequestBody @Valid TechRequestDTO techRequestDTO) {
        log.info("TechServices created");
        URI uri = URI.create("api/v1/tech/"
                + techServices.createdOrder(techRequestDTO));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public TechResponseDTO updateTechService(@RequestBody @Valid TechRequestDTO techRequestDTO) {
        return techServices.update(techRequestDTO);
    }

    @GetMapping(produces = "application/json")
    public TechResponseDTO findAllOrders() {
        log.info("Get all Orders");
        return techServices.findAllOrders();
    }

    @GetMapping("/{id}")
    public TechResponseDTO findById(@PathVariable Long id) {
        log.info("Get Services from Order by ID {}",id);
        return techServices.getById(id);
    }

    @DeleteMapping
    public ResponseEntity<?> deletedTechService(@RequestParam Long id) {
        log.info("Delete Service by ID {}", id);
        return new ResponseEntity<>(techServices.cancelOrder(id),HttpStatus.NO_CONTENT);
    }
}
