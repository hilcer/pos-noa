package com.noa.pos.model.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Dashboard7DayDto {
    private String day;
    private LocalDate date;
    private Long quantity;
    private BigDecimal price;
}

