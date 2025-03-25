package com.springlessons.testproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TechResponseDTO {
    private Long id;
    private String techName;

    public TechResponseDTO(@NotNull @NotBlank @NotEmpty String serviceName) {
    }
}
