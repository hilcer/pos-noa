package com.noa.pos.model.repository;

import com.noa.pos.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    public ProfileEntity findByName(String profileName);

    public ProfileEntity findByProfileId(int profileId);
}
