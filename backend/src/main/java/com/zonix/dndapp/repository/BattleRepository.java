package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.Battle;
import com.zonix.dndapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    Set<Battle> findByOwner(User owner);

    Optional<Battle> findByIdAndOwner(Long id, User owner);

    Optional<Battle> findFirstByOwnerOrderByCreatedAtDesc(User owner);
}
