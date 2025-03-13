package com.noa.pos.service.imp;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.model.entity.DomainEntity;
import com.noa.pos.model.repository.DomainRepository;
import com.noa.pos.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainServiceImp implements DomainService {

    private final DomainRepository domainRepository;

    @Autowired
    public DomainServiceImp(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public DomainDto saveDomain(DomainDto user) {
        var entity = domainRepository.save(dtoToEntity(user));
        return entityToDto(entity);
    }

    @Override
    public DomainDto mergeDomain(DomainDto user) {
        var entity = domainRepository.save(dtoToEntity(user));
        return entityToDto(entity);
    }

    private DomainDto entityToDto(DomainEntity entity) {
        var dto = new DomainDto();
        dto.setDomainId(entity.getDomainId());
        dto.setGroupDom(entity.getGroupDom());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        dto.setDescription(entity.getDescription());
        dto.setEnabled(entity.getEnabled());
        dto.setLastUser(entity.getLastUser());
        dto.setLastTime(entity.getLastTime());
        return dto;
    }

    private DomainEntity dtoToEntity(DomainDto domainDto) {
        var entity = domainDto.getDomainId() != null ? domainRepository.getReferenceById(domainDto.getDomainId()) : new DomainEntity();
        entity.setGroupDom(domainDto.getGroupDom());
        entity.setName(domainDto.getName());
        entity.setValue(domainDto.getValue());
        entity.setDescription(domainDto.getDescription());
        entity.setEnabled(domainDto.getEnabled());
        entity.setLastUser(domainDto.getLastUser());
        entity.setLastTime(domainDto.getLastTime());
        return entity;
    }

    @Override
    public List<DomainDto> getAllDomains() {
        return domainRepository.findAll().stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public List<DomainDto> getGropupDom(String group) {
        return domainRepository.findByGroupDom(group).stream().parallel().map(this::entityToDto).toList();
    }

    public DomainDto getDomainGroupAndName(String domainGroup, String domainName) {
        var entity = domainRepository.findByGroupDomAndName(domainGroup, domainName);
        if (entity == null) { return null; }
        return entityToDto(entity);
    }
}
