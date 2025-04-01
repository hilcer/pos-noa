package com.noa.pos.model.repository;

import com.noa.pos.model.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {

    List<DomainEntity> findByName(String name);

    List<DomainEntity> findByGroupDom(String groupDom);

    List<DomainEntity> findByGroupDomAndCompanyId(String groupDom, Integer companyId);

    DomainEntity findByGroupDomAndName(String groupDom, String domainName);

}
