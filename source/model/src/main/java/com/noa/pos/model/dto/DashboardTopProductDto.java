package com.noa.pos.model.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DashboardTopProductDto {
    private String name;
    private BigDecimal price;
    private Long quantity;
}

