package com.noa.pos.model.repository;

import com.noa.pos.model.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    CompanyEntity findByNit(String name);

}
