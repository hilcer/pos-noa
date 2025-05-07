package com.noa.pos.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

	private Integer userId;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	private String mobileNumber;
	@NotBlank
	private String fullName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String email;
	private String address;
	private String city;
	private String state;
	private String pinCode;
	private Integer profileId;
	private Boolean enabled;
	private Integer failedAttempt;
	private LocalDateTime lockTime;
	private String resetToken;
	private String lastUser;
	private LocalDateTime lastTime;
	private Integer companyId;
	private Integer sucursalId;
	private String nameCompany;
	private String descriptionProfile;
}
