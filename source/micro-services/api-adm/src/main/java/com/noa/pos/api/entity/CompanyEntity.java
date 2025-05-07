package com.noa.pos.api.entity;

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
@Table( name = "company", uniqueConstraints = @UniqueConstraint(columnNames = {"nit"}))
@Entity
public class CompanyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer companyId;
	private String nameCompany;
	private String nit;
	private Boolean enabled;
	private String lastUser;
	private LocalDateTime lastTime;

}
