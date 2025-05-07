package com.noa.pos.api.repository;

import com.noa.pos.api.entity.ProfileMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileMenuRepository extends JpaRepository<ProfileMenuEntity, Integer> {
}
