package com.noa.pos.service.imp;

import com.noa.pos.dto.CompanyDto;
import com.noa.pos.model.entity.CompanyEntity;
import com.noa.pos.model.repository.CompanyRepository;
import com.noa.pos.model.repository.DomainRepository;
import com.noa.pos.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImp implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImp(CompanyRepository domainRepository) {
        this.companyRepository = domainRepository;
    }

    @Override
    public CompanyDto saveCompany(CompanyDto companyDto) {
        var entity = companyRepository.save(dtoToEntity(companyDto));
        return entityToDto(entity);
    }

    private CompanyDto entityToDto(CompanyEntity companyEntity) {
        var companyDto = new CompanyDto();
        companyDto.setCompanyId(companyEntity.getCompanyId());
        companyDto.setNameCompany(companyEntity.getNameCompany());
        companyDto.setNit(companyEntity.getNit());
        companyDto.setEnabled(companyEntity.getEnabled());
        companyDto.setLastUser(companyEntity.getLastUser());
        companyDto.setLastTime(companyEntity.getLastTime());
        return companyDto;
    }

    private CompanyEntity dtoToEntity(CompanyDto dto) {
        var entity = new CompanyEntity();
//        entity.setCompanyId(dto.getCompanyId());
        entity.setNameCompany(dto.getNameCompany());
        entity.setNit(dto.getNit());
        entity.setEnabled(dto.getEnabled());
        entity.setLastUser(dto.getLastUser());
        entity.setLastTime(dto.getLastTime());
        return entity;
    }

    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream().parallel().map(this::entityToDto).toList();
    }

    @Override
    public CompanyDto getByNit(String nit) {
        return entityToDto(companyRepository.findByNit(nit));
    }
}
