package com.noa.pos.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private Integer companyId;
    private Integer sucursalId;
    private String code;
    private String name;
    private String description;
    private String price;
    private String productType;
    private String image;
    private String stock;
    private String marca;
    private String modelo;
    private String color;
    private String initStamp;
    private Boolean enabled;
    private String lastUser;
    private LocalDate lastTime;
}
