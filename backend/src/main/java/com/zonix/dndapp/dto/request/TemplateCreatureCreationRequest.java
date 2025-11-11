package com.zonix.dndapp.dto.request;

import com.zonix.dndapp.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record TemplateCreatureCreationRequest(
         String name,
         String size,
         String creatureType,
         String alignment,
         Integer armorClass,
         String armorDescription,
         Integer hitPoints,
         String hitDice,

         Integer strength,
         Integer dexterity,
         Integer constitution,
         Integer intelligence,
         Integer wisdom,
         Integer charisma,

         List<String> savingThrows,

         String damageResistances,
         String damageVulnerabilities,
         String damageImmunities,
         String conditionImmunities,
         String senses,
         String languages,

         Map<String, String> actions,
         Map<String, String> bonusActions,
         Map<String, String> abilities,
         Set<String> environments,
         Map<String, String> legendaryActions,
         Map<String, String> reactions,
         Map<String, String> speeds,
         List<String> skills,

         String challengeRating,
         String description,
         String legendaryDescription
) {
}
