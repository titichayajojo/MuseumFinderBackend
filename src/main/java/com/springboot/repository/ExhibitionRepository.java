package com.springboot.repository;

import com.springboot.entity.Exhibition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    Optional<Exhibition> findByName(String name);
    Optional<Exhibition> findById(Long id);

    Boolean existsByName(String name);
}
