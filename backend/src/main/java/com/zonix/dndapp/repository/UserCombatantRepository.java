package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.UserCombatant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserCombatantRepository extends JpaRepository<UserCombatant, Long> {
    Set<UserCombatant> findUserCombatantsByOwnerEmail(String ownerEmail);

    void deleteByIdAndOwner(Long id, User owner);

    Optional<UserCombatant> getByIdAndOwner(Long id, User owner);

    Optional<UserCombatant> findByIdAndOwner(Long id, User owner);

    Optional<UserCombatant> findById(Long id);
}
