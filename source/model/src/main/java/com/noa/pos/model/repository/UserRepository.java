package com.noa.pos.model.repository;

import com.noa.pos.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByEmail(String email);

	public UserEntity findByUser(String user);

	public List<UserEntity> findByProfileId(Integer profileId);

	public UserEntity findByResetToken(String token);

	public Boolean existsByEmail(String email);
}
