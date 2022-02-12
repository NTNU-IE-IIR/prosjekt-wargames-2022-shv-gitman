package org.ntnu.wargames;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        assertEquals(4,commanderUnit.getAttackBonus());
        assertEquals(1,commanderUnit.getResistBonus());
    }

    @Test
    public void testPredefined_CommanderUnit_Creation() {
        CommanderUnit commanderUnit = new CommanderUnit("Commander",180);

        assertEquals(25,commanderUnit.getAttack());
        assertEquals(15,commanderUnit.getArmor());
    }
}
