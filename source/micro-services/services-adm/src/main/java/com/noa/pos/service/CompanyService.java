package com.noa.pos.service;

import com.noa.pos.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    public CompanyDto saveCompany(CompanyDto user);

    public List<CompanyDto> getAllCompanies();

    CompanyDto getByNit(String nit);

}
