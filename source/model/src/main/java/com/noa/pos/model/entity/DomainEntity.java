package com.noa.pos.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "domain")
public class DomainEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer domainId;
	private String groupDom;
	private String name;
	private String value;
	private String description;
	private Boolean enabled;
	private String lastUser;
	private LocalDateTime lastTime;
	private Integer companyId;
}
