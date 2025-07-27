package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.entity.UserCombatant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateCreatureRepository extends JpaRepository<TemplateCreature, Long> {

    Optional<TemplateCreature> findTemplateCreatureById(@Param("id") Long id);

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

    @EntityGraph(attributePaths = {
            "actions",
            "bonusActions",
            "abilities",
            "environments",
            "legendaryActions",
            "reactions",
            "speeds",
            "skills"
    })
    @Query("SELECT t FROM TemplateCreature t WHERE t.id = :id")
    Optional<TemplateCreature> findById(@Param("id") Long templateId);
}
