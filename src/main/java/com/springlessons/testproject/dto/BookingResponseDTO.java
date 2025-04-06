package com.springlessons.testproject.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingResponseDTO(
    @Positive
    @NotNull
    Long id,

    @NotBlank
    @NotEmpty
    String name,

    @Positive
    @NotNull
    Double price
) {}
