package com.noa.pos.service;

import com.noa.pos.dto.DomainDto;

import java.util.List;

public interface DomainService {

    public DomainDto saveDomain(DomainDto user);

    public List<DomainDto> getAllUsers();

}
