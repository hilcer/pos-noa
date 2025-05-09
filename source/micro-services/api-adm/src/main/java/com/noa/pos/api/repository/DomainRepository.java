package com.noa.pos.api.repository;

import com.noa.pos.api.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {

    List<DomainEntity> findByName(String name);

    List<DomainEntity> findByGroupDom(String groupDom);

    List<DomainEntity> findByGroupDomAndCompanyId(String groupDom, Integer companyId);

    DomainEntity findByGroupDomAndName(String groupDom, String domainName);

}
