package com.noa.pos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

	private Integer userId;

	@NotBlank
	private String user;

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

	private Date lockTime;

	private String resetToken;

	private String lastUser;

	private Date lastTime;

}
