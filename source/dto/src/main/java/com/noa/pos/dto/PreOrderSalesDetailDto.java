package com.noa.pos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PreOrderSalesDetailDto {
    private Long orderSalesDetailId;
    private Long orderSalesId;
    private String productId;
    private BigDecimal price;
    private String name;
    private Integer quantity;
    private String lastUser;
    private LocalDateTime lastTime;
}


//id_detalleventa int not null primary key auto_increment,
//id_venta  int not null,
//id_producto int not null,
//monto decimal(10,2),
//cantidad varchar(30),
//fecha_registro datetime not null