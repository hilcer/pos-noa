package com.noa.pos.api.service.imp;

import com.noa.pos.api.dto.ProfileDto;
import com.noa.pos.api.entity.ProfileEntity;
import com.noa.pos.api.repository.ProfileRepository;
import com.noa.pos.api.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<ProfileDto> getAllEnabledProfiles() {
        return profileRepository.getAllEnabledProfiles().stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public ProfileDto getProfileByDescription(String description) {
        return entityToDto(profileRepository.findByDescription(description));
    }
}
