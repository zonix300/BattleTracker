package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.Battle;
import com.zonix.dndapp.entity.Lobby;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
    Page<Lobby> findAll(Pageable pageable);

    Page<Lobby> findAllByNameStartingWith(String name, Pageable pageable);

    @Query("""
        select distinct l.id, l.createdAt
        from Lobby l
        where l.name like concat(:name, '%')
    """)
    List<Long> findLobbyIdsByNameStartingWith(@Param("name") String name, Pageable pageable);

    @Query("""
        select l from Lobby l
        where not exists (
            select 1 from LobbyUser lu
            where lu.lobby = l
            )
    """)
    List<Lobby> findEmptyLobbies();

    Optional<Lobby> findByBattle(Battle battle);


}
