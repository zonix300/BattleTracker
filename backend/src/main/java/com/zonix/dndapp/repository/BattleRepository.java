package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.Battle;
import com.zonix.dndapp.entity.Role;
import com.zonix.dndapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    Set<Battle> findAllByRoleAndOwner(Role role, User owner);

    @Query("select b from Battle b left join fetch b.userCombatants where b.id = :id")
    Optional<Battle> findById(Long id);

    Optional<Battle> findFirstByOwnerOrderByCreatedAtDesc(User owner);
}
