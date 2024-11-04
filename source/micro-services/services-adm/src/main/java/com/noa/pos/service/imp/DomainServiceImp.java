package com.noa.pos.service.imp;

import com.noa.pos.dto.DomainDto;
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
        return null;
    }

    @Override
    public List<DomainDto> getAllUsers() {
        return List.of();
    }
}
