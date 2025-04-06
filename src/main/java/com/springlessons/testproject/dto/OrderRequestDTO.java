package com.springlessons.testproject.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record OrderRequestDTO(
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
