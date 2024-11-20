package com.noa.pos.service.imp;

import com.noa.pos.dto.ProfileDto;
import com.noa.pos.model.entity.ProfileEntity;
import com.noa.pos.model.repository.ProfileRepository;
import com.noa.pos.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImp implements ProfileService {


    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImp(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileDto getProfileByUserId(Integer userId) {

        var entity = profileRepository.findByProfileId(userId);

        if (entity == null) { return null; }

        return entityToDto(entity);
    }

    private ProfileDto entityToDto(ProfileEntity entity) {
        ProfileDto dto = new ProfileDto();
        dto.setProfileId(entity.getProfileId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setEnabled(entity.getEnabled());
        dto.setLastUser(entity.getLastUser());
        dto.setLastTime(entity.getLastTime());
        return dto;
    }
}