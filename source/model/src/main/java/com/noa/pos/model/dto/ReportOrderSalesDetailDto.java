package com.noa.pos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportOrderSalesDetailDto {
	private Long orderSalesDetailId;
	private Long orderSalesId;
	private Integer productId;
	private String name;
	private BigDecimal price;
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