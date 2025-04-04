package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.TemplateCreature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CombatantRepository extends JpaRepository<TemplateCreature, Long> {

    Optional<TemplateCreature> findById(Long id);
}
