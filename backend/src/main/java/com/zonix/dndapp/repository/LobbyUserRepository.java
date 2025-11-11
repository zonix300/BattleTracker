package com.zonix.dndapp.repository;

import com.zonix.dndapp.dto.entity.LobbyFlatDTO;
import com.zonix.dndapp.entity.Lobby;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.LobbyUser;
import com.zonix.dndapp.entity.UserLobbyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface LobbyUserRepository extends JpaRepository<LobbyUser, UserLobbyId> {

    @Query("""
               select new com.zonix.dndapp.dto.entity.LobbyFlatDTO(
                   l.id,
                   l.name,
                   l.owner.username,
                   l.createdAt,
                   u.username,
                   ul.role,
                   case when l.passwordHash is not null then true else false end
               )
               from LobbyUser ul
               join ul.lobby l
               join ul.user u
               where l.id in :ids
           """)
    List<LobbyFlatDTO> getFlatLobbiesByNameStartingWith(@Param("ids") List<Long> ids);

    LobbyUser user(User user);

    boolean existsLobbyUserByUser(User user);

    LobbyUser findLobbyUserByUser(User user);

    boolean existsLobbyUserByLobbyAndUser(Lobby lobby, User user);

    boolean existsLobbyUserByUserAndLobby(User user, Lobby lobby);

    List<LobbyUser> findAllByDisconnectTimeBefore(Instant disconnectTimeBefore);
}
