package com.noa.pos.api.repository;

import com.noa.pos.api.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    CompanyEntity findByNit(String name);

}
