package com.springlessons.testproject.controller;

import com.springlessons.testproject.dto.TechRequestDTO;
import com.springlessons.testproject.dto.TechResponseDTO;
import com.springlessons.testproject.services.TechServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/api/v1/tech")
@RestController
@AllArgsConstructor
public class TechServicesController {

    private final TechServices techServices;

    @PostMapping()
    public String createdTechService() {
        return techServices.createdTechService();
    }

    @PutMapping()
    public String updateTechService(@RequestBody @Valid TechRequestDTO techRequestDTO) {
        return techServices.update(techRequestDTO);
    }

    @GetMapping
    public String findAllServices() {
        return techServices.findAllServices();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id) {
        return techServices.getById(id);
    }
}
