package com.noa.pos.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CompanyDto {
	private Integer companyId;
	private String nameCompany;
	private String nit;
	private Boolean enabled;
	private String lastUser;
	private LocalDate lastTime;

}
