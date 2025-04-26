package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.TemplateCreature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateCreatureRepository extends JpaRepository<TemplateCreature, Long> {

    Optional<TemplateCreature> findById(Long id);
}
