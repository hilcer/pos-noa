package com.noa.pos.model.repository;

import com.noa.pos.model.entity.ProfileMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileMenuRepository extends JpaRepository<ProfileMenuEntity, Integer> {
}
