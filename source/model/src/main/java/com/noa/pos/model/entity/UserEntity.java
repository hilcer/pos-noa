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
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames={"username"}, name = "UX_USER"))
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String username;
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
	private LocalDateTime lockTime;
	private String resetToken;
	private String lastUser;
	private LocalDateTime lastTime;
	private Integer companyId;

}
