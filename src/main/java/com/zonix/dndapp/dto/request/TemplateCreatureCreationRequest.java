package com.zonix.dndapp.dto.request;

import com.zonix.dndapp.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
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

         Integer strengthSave,
         Integer dexteritySave,
         Integer constitutionSave,
         Integer intelligenceSave,
         Integer wisdomSave,
         Integer charismaSave,

         String damageResistances,
         String damageVulnerabilities,
         String damageImmunities,
         String conditionImmunities,
         String senses,
         String languages,

         List<Action> actions,
         List<BonusAction> bonusActions,
         List<Ability> abilities,
         Set<String> environments,
         List<LegendaryAction> legendaryActions,
         List<Reaction> reactions,
         List<CreatureSpeed> speeds,
         List<CreatureSkills> skills,

         String challengeRating,
         String description,
         String legendaryDescription,
         String documentTitle

) {
}
