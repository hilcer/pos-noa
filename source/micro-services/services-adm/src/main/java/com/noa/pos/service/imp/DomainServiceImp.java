package com.noa.pos.service.imp;

import com.noa.pos.dto.DomainDto;
import com.noa.pos.model.entity.DomainEntity;
import com.noa.pos.model.repository.DomainRepository;
import com.noa.pos.model.repository.UserRepository;
import com.noa.pos.service.DomainService;
import com.noa.pos.service.config.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainServiceImp implements DomainService {

    private final DomainRepository domainRepository;
    private final UserRepository userRepository;

    @Autowired
    public DomainServiceImp(DomainRepository domainRepository, UserRepository userRepository) {
        this.domainRepository = domainRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DomainDto saveDomain(DomainDto user) {
        var entity = domainRepository.save(dtoToEntity(user));
        return entityToDto(entity);
    }

    @Override
    @CacheEvict(cacheNames = {CacheConfig.CACHE_DOMAIN,
            CacheConfig.CACHE_DOMAIN_GROUP,
            CacheConfig.CACHE_DOMAIN_GROUP_LIST,
            CacheConfig.CACHE_DOMAIN_GROUP_USER_LIST}, allEntries = true)
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
    @Cacheable(cacheNames = CacheConfig.CACHE_DOMAIN_GROUP_LIST, unless = "#result == null")
    public List<DomainDto> getGropupDom(String group) {
        return domainRepository.findByGroupDom(group).stream().parallel().map(this::entityToDto).toList();
    }

    @Cacheable(cacheNames = CacheConfig.CACHE_DOMAIN_GROUP_USER_LIST, unless = "#result == null")
    public List<DomainDto> getGropupDom(String group, String user) {
        var userEntity = userRepository.findByUsername(user);
        return domainRepository.findByGroupDomAndCompanyId(group, userEntity.getCompanyId()).stream().parallel().map(this::entityToDto).toList();
    }

    @Cacheable(cacheNames = CacheConfig.CACHE_DOMAIN_GROUP, unless = "#result == null")
    public DomainDto getDomainGroupAndName(String domainGroup, String domainName) {
        var entity = domainRepository.findByGroupDomAndName(domainGroup, domainName);
        if (entity == null) { return null; }
        return entityToDto(entity);
    }
}
