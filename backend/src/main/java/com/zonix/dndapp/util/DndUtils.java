package com.zonix.dndapp.util;

import com.zonix.dndapp.entity.TemplateCreature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DndUtils {
    private static final Map<String, Integer> CR_TO_XP = new HashMap<>();
    private static final Random random = new Random();
    private static final Pattern DICE_PATTERN = Pattern.compile("(\\d+)d(\\d+)([+-]\\d+)?");

    static {
        CR_TO_XP.put("0", 0);
        CR_TO_XP.put("1/8", 25);
        CR_TO_XP.put("1/4", 50);
        CR_TO_XP.put("1/2", 100);
        CR_TO_XP.put("1", 200);
        CR_TO_XP.put("2", 450);
        CR_TO_XP.put("3", 700);
        CR_TO_XP.put("4", 1100);
        CR_TO_XP.put("5", 1800);
        CR_TO_XP.put("6", 2300);
        CR_TO_XP.put("7", 2900);
        CR_TO_XP.put("8", 3900);
        CR_TO_XP.put("9", 5000);
        CR_TO_XP.put("10", 5900);
        CR_TO_XP.put("11", 7200);
        CR_TO_XP.put("12", 8400);
        CR_TO_XP.put("13", 10000);
        CR_TO_XP.put("14", 11500);
        CR_TO_XP.put("15", 13000);
        CR_TO_XP.put("16", 15000);
        CR_TO_XP.put("17", 18000);
        CR_TO_XP.put("18", 20000);
        CR_TO_XP.put("19", 22000);
        CR_TO_XP.put("20", 25000);
        CR_TO_XP.put("21", 33000);
        CR_TO_XP.put("22", 41000);
        CR_TO_XP.put("23", 50000);
        CR_TO_XP.put("24", 62000);
        CR_TO_XP.put("25", 75000);
        CR_TO_XP.put("26", 90000);
        CR_TO_XP.put("27", 105000);
        CR_TO_XP.put("28", 120000);
        CR_TO_XP.put("29", 135000);
        CR_TO_XP.put("30", 155000);
    }

    private DndUtils() {
    }

    public static int calculateModifier(int stat) {
        return (int) Math.floor((stat - 10) / 2.0);
    }

    public static String getFormattedModifier(int stat) {
        return stat + " (" + (calculateModifier(stat) >= 0 ? "+" : "") + calculateModifier(stat) + ")";
    }

    public static Integer getXpForCr(String cr) {
        return CR_TO_XP.get(cr);
    }

    public static Integer getProficiencyForCr(String challengeRating) {
        int cr = Integer.parseInt(challengeRating);

        if (cr < 5) return 2;
        if (cr < 9) return 3;
        if (cr < 13) return 4;
        if (cr < 17) return 5;
        return 6;
    }

    public static int roll(int sides) {
        return random.nextInt(sides) + 1;
    }

    public static int roll(String diceNotation) {
        String normalized = diceNotation.replaceAll("\\s+", "");
        Matcher matcher = DICE_PATTERN.matcher(normalized);

        if (!matcher.matches()) {
            return 0;
        }

        int count = Integer.parseInt(matcher.group(1));
        int sides = Integer.parseInt(matcher.group(2));
        int modifier = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;

        int total = 0;
        for (int i = 0; i < count; i++) {
            total += roll(sides);
        }
        return total + modifier;
    }

    public static int calculateProficientSkillValue(TemplateCreature creature, String skillName) {
        int pb = getProficiencyForCr(creature.getChallengeRating());
        for (Skill skill : Skill.values()) {
            if (skillName.equals(skill.getName())) {
                return switch (skill.getAbility()) {
                    case "strength" -> calculateModifier(creature.getStrength()) + pb;
                    case "dexterity" -> calculateModifier(creature.getDexterity()) + pb;
                    case "constitution" -> calculateModifier(creature.getConstitution()) + pb;
                    case "intelligence" -> calculateModifier(creature.getIntelligence()) + pb;
                    case "wisdom" -> calculateModifier(creature.getWisdom()) + pb;
                    case "charisma" -> calculateModifier(creature.getCharisma()) + pb;
                    default -> 0;
                };
            }
        }
        return 0;
    }
}
