package com.noa.pos.model.repository;

import com.noa.pos.model.entity.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<DomainEntity, Integer> {
}
