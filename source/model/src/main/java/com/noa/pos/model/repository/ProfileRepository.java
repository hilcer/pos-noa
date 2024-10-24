package com.noa.pos.model.repository;

import com.noa.pos.model.entity.Menu;
import com.noa.pos.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
