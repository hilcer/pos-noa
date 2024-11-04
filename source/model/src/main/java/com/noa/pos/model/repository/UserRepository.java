package com.noa.pos.model.repository;

import com.noa.pos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public List<User> findByProfileId(Integer profileId);

	public User findByResetToken(String token);

	public Boolean existsByEmail(String email);
}
