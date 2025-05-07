package com.noa.pos.dto;


import lombok.*;

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
