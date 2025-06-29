package com.zonix.dndapp.dto.request;

import com.zonix.dndapp.entity.StatusEffect;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record EffectBatchRequest(
        List<ItemEffectUpdate> updates

) {
    public record ItemEffectUpdate(
            @NotNull Long itemId,
            @NotNull Set<StatusEffect> effects,
            @NotNull boolean shouldAdd
    ) {

    }
}
