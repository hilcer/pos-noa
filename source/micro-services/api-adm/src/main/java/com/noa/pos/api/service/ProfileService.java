package com.noa.pos.api.service;

import com.noa.pos.api.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto getProfileByUserId(Integer userId);

    List<ProfileDto> getAllEnabledProfiles();

    ProfileDto getProfileByDescription(String description);
}
