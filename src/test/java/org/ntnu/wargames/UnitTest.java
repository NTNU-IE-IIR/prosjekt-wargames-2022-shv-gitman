package org.ntnu.wargames;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void test_InfantryUnit_Creation() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry",100,25,15);

        assertEquals("Infantry",infantryUnit.getName());
        assertEquals(100,infantryUnit.getHealth());
        assertEquals(25,infantryUnit.getAttack());
        assertEquals(15,infantryUnit.getArmor());
        assertEquals(2,infantryUnit.getAttackBonus());
        assertEquals(1,infantryUnit.getResistBonus());
    }

    @Test
    public void testPredefined_InfantryUnit_Creation() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry",100);

        assertEquals(15,infantryUnit.getAttack());
        assertEquals(10,infantryUnit.getArmor());
    }

    @Test
    public void test_CommanderUnit_Creation() {
        CommanderUnit commanderUnit = new CommanderUnit("Commander",180,50,20);

        assertEquals("Commander",commanderUnit.getName());
        assertEquals(180,commanderUnit.getHealth());
        assertEquals(50,commanderUnit.getAttack());
        assertEquals(20,commanderUnit.getArmor());
        assertEquals(2,commanderUnit.getAttackBonus());
        assertEquals(1,commanderUnit.getResistBonus());
    }

    @Test
    public void testPredefined_CommanderUnit_Creation() {
        CommanderUnit commanderUnit = new CommanderUnit("Commander",180);

        assertEquals(25,commanderUnit.getAttack());
        assertEquals(15,commanderUnit.getArmor());
    }

    @Test
    public void add_Unit_To_Army() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry",100);
        Army army = new Army("Test army");

        army.add(infantryUnit);

        assertTrue(army.hasUnits());
    }

    @Test
    public void remove_Unit_From_Army() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
        Army army = new Army("Test army");

        // Adds unit
        army.add(infantryUnit);
        // Removes unit
        army.remove(infantryUnit);

        assertFalse(army.hasUnits());
    }

    @Test
    public void test_First_Attack_Cavalry_Infantry() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);
        InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);

        cavalryUnit.attack(infantryUnit);

        // Cavalry attack: 20 + 6 (first attack bonus)
        // Infantry defence: 10 + 1 (resist bonus)
        assertEquals(85,infantryUnit.getHealth());
    }

    @Test
    public void test_Second_Attack_Cavalry_Infantry() {
        CavalryUnit cavalryUnit = new CavalryUnit("Cavalry",100);
        InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);

        // First attack
        // Cavalry attack: 20 + 6 (first attack bonus)
        // Infantry defence: 10 + 1 (resist bonus)
        // expected: 100 - 26 + 11 = 85
        cavalryUnit.attack(infantryUnit);

        // Second attack
        // Cavalry attack: 20 + 2 (second attack bonus)
        // Infantry defence: 10 + 1 (resist bonus)
        // expected: 85 - 22 + 11 = 74
        cavalryUnit.attack(infantryUnit);

        assertEquals(74,infantryUnit.getHealth());
    }

    @Test
    public void test_RangedUnit_ResistanceBonus() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
        RangedUnit rangedUnit = new RangedUnit("Archer",100);

        // First attack
        // Infantry attack: 15 + 2 (attack bonus)
        // Archer defence: 8 + 6 (resist bonus)
        // expected: 100 - 17 + 14 = 97
        infantryUnit.attack(rangedUnit);

        // Second attack
        // Infantry attack: 15 + 2 (attack bonus)
        // Archer defence: 8 + 4 (resist bonus)
        // expected: 100 - 17 + 12 = 92
        infantryUnit.attack(rangedUnit);

        // Third attack
        // Infantry attack: 15 + 2 (attack bonus)
        // Archer defence: 8 + 2 (resist bonus)
        // expected: 100 - 17 + 10 = 85
        infantryUnit.attack(rangedUnit);

        assertEquals(85,rangedUnit.getHealth());
    }
}
