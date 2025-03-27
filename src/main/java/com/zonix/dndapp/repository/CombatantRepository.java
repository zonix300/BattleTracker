package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.Combatant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CombatantRepository extends JpaRepository<Combatant, Long> {

    Optional<Combatant> findById(Long id);
    Optional<Combatant> findByName(String name);
    List<Combatant> findByAc(Integer ac);
    List<Combatant> findByHp(Integer hp);
}
