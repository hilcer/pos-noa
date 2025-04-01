package com.noa.pos.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VentaProductsDto {
    private List<ProductDto> products;
    private List<DomainDto> productTypes;
}
