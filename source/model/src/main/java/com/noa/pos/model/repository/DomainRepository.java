package com.noa.pos.model.repository;

import com.noa.pos.model.entity.Domain;
import com.noa.pos.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, Integer> {
}
