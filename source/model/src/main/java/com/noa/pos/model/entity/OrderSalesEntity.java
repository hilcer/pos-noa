package com.noa.pos.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "order_sales")
public class OrderSalesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSalesId;
    private Integer ticketNumber;
    private BigDecimal totalAmount;
    private String state;
    private String dateRegsiter;
    private Integer companyId;
    private Integer sucursalId;
    private String lastUser;
    private LocalDate lastTime;
}


//create table venta
//(
//id_venta bigint not null primary key auto_increment,
//usuario varchar(50) not null,
//monto_total decimal (10,2),
//estado varchar(15),
//fecha date,
//fecha_registro datetime not null,
//impresiones int,
//nro_ticket varchar(10) not null
//);
//
//create table detalle_venta
//(
//id_detalleventa int not null primary key auto_increment,
//id_venta  int not null,
//id_producto int not null,
//monto decimal(10,2),
//cantidad varchar(30),
//fecha_registro datetime not null
//);