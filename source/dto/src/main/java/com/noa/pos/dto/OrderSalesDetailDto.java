package com.noa.pos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderSalesDetailDto {
    private Long orderSalesDetailId;
    private Long orderSalesId;
    private String productId;
    private BigDecimal price;
    private String description;
    private Integer quantity;
    private String lastUser;
    private LocalDate lastTime;
}


//id_detalleventa int not null primary key auto_increment,
//id_venta  int not null,
//id_producto int not null,
//monto decimal(10,2),
//cantidad varchar(30),
//fecha_registro datetime not null