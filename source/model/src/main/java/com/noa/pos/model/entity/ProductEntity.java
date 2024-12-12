package com.noa.pos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
@Entity
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
    private LocalDateTime lastTime;
}
