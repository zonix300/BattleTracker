package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.LobbyRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LobbyRequestRepository extends JpaRepository<LobbyRequest, Long> {
}
