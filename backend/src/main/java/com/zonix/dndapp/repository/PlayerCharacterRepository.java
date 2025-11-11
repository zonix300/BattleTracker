package com.zonix.dndapp.repository;

import com.zonix.dndapp.dto.entity.combatant.PlayerCombatantDTO;
import com.zonix.dndapp.dto.request.PlayerCharacterSearchRequest;
import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerCharacterRepository extends CrudRepository<PlayerCharacter, Long> {
    List<PlayerCharacter> findAllByOwner(User owner);

    @Query(
    value = """
        select
            pc.id as id,
            pc.name as name,
            (pc.hp ->> 'current'):: int as currentHp,
            (pc.hp ->> 'max')::int as maxHp,
            (pc.hp ->> 'temporary'):: int as temporaryHp,
            pc.armor_class as armor_class,
            (pc.abilities ->> 'dexterity')::int as dexterity
        from player_characters pc
        where lower(pc.name) like lower(concat(cast(:#{#request.name} as text), '%')) and pc.created_by = :#{#owner.id}
    """,
    countQuery = """
        select count (*)
        from player_characters pc
        where lower(pc.name) like lower(concat(cast(:#{#request.name} as text), '%')) and pc.created_by = :#{#owner.id}
    """,
    nativeQuery = true)
    Page<PlayerCombatantDTO> searchCombatants(@Param("request") PlayerCharacterSearchRequest request, @Param("owner") User owner, Pageable pageable);
}
