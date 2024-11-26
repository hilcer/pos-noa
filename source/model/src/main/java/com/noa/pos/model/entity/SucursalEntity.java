package com.noa.pos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table( name = "sucursal", uniqueConstraints = @UniqueConstraint(columnNames = {"sucursalId", "companyId"}))
@Entity
public class SucursalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sucursalId;
	private Integer companyId;
	private String nameSucursal;
	private String sucursalDescription;
	private String sucursalNit;
	private Boolean enabled;
	private String lastUser;
	private LocalDate lastTime;

}
