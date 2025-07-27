package com.zonix.dndapp.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ActionMapperHelper {
    public static <T extends BaseAction> Set<T> mapActionMap(
            Map<String, String> actionMap,
            BiFunction<String, String, T> entityCreator
    ) {
        if (actionMap == null || actionMap.isEmpty()) {
            return null;
        }
        return actionMap.entrySet().stream()
                .map(e -> entityCreator.apply(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());

    }
}
