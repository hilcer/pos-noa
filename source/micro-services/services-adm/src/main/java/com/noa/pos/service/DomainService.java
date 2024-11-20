package com.noa.pos.service;

import com.noa.pos.dto.DomainDto;

import java.util.List;

public interface DomainService {

    public DomainDto saveDomain(DomainDto user);

    List<DomainDto> getAllDomains();

    public List<DomainDto> getGropupDom(String group);

    DomainDto getDomainGroupAndName(String domainGroup, String domainName);
}
