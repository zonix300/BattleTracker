package com.zonix.dndapp.exception;

public class BattleNotFoundException extends RuntimeException {
    public BattleNotFoundException(String message) {
        super(message);
    }
}
