package com.noa.pos.service;

import com.noa.pos.dto.ProfileDto;

public interface ProfileService {

    ProfileDto getProfileByUserId(Integer userId);
}
