package com.springlessons.testproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyRevenueDto {
    private LocalDate date;
    private Double revenue;
}
