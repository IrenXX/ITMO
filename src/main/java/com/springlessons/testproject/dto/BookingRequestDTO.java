package com.springlessons.testproject.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record BookingRequestDTO(
        @NotNull
        @Positive
        Long id,

        @NotNull
        @NotBlank
        @NotEmpty
        String name,

        @NotNull
        @Positive
        Double price) {

}
