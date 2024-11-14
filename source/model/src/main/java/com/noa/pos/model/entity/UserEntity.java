package com.noa.pos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames={"user"}, name = "UX_USER"))
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
