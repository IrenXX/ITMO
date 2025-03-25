package com.springlessons.testproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class TechRequestDTO {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String serviceName;
}
