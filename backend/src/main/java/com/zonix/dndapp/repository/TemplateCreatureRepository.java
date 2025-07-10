package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TemplateCreatureRepository extends JpaRepository<TemplateCreature, Long> {

    Optional<TemplateCreature> findById(@Param("id") Long id);

    @Query("""
        select new com.zonix.dndapp.entity.Combatant(
        t.id,
        t.name,
        t.hitDice,
        t.armorClass,
        t.dexterity
        ) 
        from TemplateCreature t
        where t.id = :id
    """)
    Optional<Combatant> findCreatureSummariesById(@Param("id") Long id);

    @Query("""
        select new com.zonix.dndapp.entity.Combatant(
        t.id,
        t.name,
        t.hitDice,
        t.armorClass,
        t.dexterity
        ) 
        from TemplateCreature t 
        where lower(t.name) like lower(concat('%', :name, '%')) and lower(t.documentTitle) = '5e core rules'
    """)
    Page<Combatant> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}
