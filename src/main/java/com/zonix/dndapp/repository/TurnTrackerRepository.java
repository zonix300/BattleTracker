package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.TurnTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnTrackerRepository extends JpaRepository<TurnTracker, Long> {
    List<TurnTracker> findAllByOrderByTurnOrderAsc();
}
