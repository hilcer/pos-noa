package com.noa.pos.model.repository;

import com.noa.pos.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    public ProfileEntity findByName(String profileName);

    public ProfileEntity findByProfileId(int profileId);

    @Query("select p from ProfileEntity p where p.enabled = true")
    List<ProfileEntity> getAllEnabledProfiles();

    ProfileEntity findByDescription(String description);
}
