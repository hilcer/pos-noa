package com.noa.pos.api.dto;

import lombok.*;

import java.time.LocalDateTime;

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
	private LocalDateTime lastTime;

}
