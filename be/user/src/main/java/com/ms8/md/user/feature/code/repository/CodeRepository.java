package com.ms8.md.user.feature.code.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms8.md.user.feature.code.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Integer> {

	Optional<Code> findByName(String name);
}
