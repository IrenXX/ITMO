package com.springlessons.testproject.services;

import com.springlessons.testproject.dto.TechRequestDTO;
import com.springlessons.testproject.dto.TechResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TechServices {

    public TechResponseDTO update(@Valid TechRequestDTO techRequestDTO) {
        return null;
    }

    public TechResponseDTO getById(@Valid Long id) {
        return null;
    }

    public TechResponseDTO findAllOrders() {
        return null;
    }

    public Long createdOrder(@Valid TechRequestDTO techRequestDTO) {
        return 1L;
    }

    public String cancelOrder(Long id) {
        return "cancelTechService";
    }
}
