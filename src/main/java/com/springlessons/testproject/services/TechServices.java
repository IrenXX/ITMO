package com.springlessons.testproject.services;

import com.springlessons.testproject.dto.TechRequestDTO;
import com.springlessons.testproject.dto.TechResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TechServices {

    public String update(@Valid TechRequestDTO techRequestDTO) {
        return "services updated";
    }

    public String getById(@Valid Long id) {
        return "find "+id;
    }

    public String findAllServices() {
        return "findAllServices";
    }

    public String createdTechService() {
        return "createdTechService";
    }
}
