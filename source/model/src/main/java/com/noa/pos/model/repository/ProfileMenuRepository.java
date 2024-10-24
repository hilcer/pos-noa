package com.noa.pos.model.repository;

import com.noa.pos.model.entity.Profile;
import com.noa.pos.model.entity.ProfileMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileMenuRepository extends JpaRepository<ProfileMenu, Integer> {
}
