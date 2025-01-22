package com.noa.pos.service;

import com.noa.pos.dto.ProfileDto;

import java.util.List;

public interface ProfileService {

    ProfileDto getProfileByUserId(Integer userId);

    List<ProfileDto> getAllEnabledProfiles();

    ProfileDto getProfileByDescription(String description);
}
