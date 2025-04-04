package com.zonix.dndapp.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public final class IdGeneratorService {
    private static final AtomicLong idCounter = new AtomicLong(1);

    private IdGeneratorService() {}

    public static Long generateId() {
        return idCounter.getAndIncrement();
    }

    public static void setInitialCounter(Long highestExistingId) {
        idCounter.set(highestExistingId);
    }
}
