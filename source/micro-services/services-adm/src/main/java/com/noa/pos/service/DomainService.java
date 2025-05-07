package com.noa.pos.service;


import com.noa.pos.dto.DomainDto;

import java.util.List;

public interface DomainService {

    public DomainDto saveDomain(DomainDto user);

    public DomainDto mergeDomain(DomainDto user);

    List<DomainDto> getAllDomains();

    public List<DomainDto> getGropupDom(String group);

    public List<DomainDto> getGropupDom(String group, String user);

    DomainDto getDomainGroupAndName(String domainGroup, String domainName);
}
