package com.noa.pos.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

	private Integer userId;

	private String user;

	private String password;

	private String mobileNumber;

	private String fullName;

	private String lastName;

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
