package com.noa.pos.dto;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {

    private Integer productId;
    private Integer companyId;
    private Integer sucursalId;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private String productType;
    private String image;
    private String stock;
    private String marca;
    private String modelo;
    private String color;
    private String initStamp;
    private Boolean enabled;

    private String lastUser;

    private LocalDateTime lastTime;
}
