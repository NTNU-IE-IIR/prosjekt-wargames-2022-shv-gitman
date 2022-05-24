package no.ntnu.idatx2001.wargames.model.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InfantryUnitTest {

    @Test
    public void testInfantryUnitCreation() {
        InfantryUnit unit = new InfantryUnit("Infantry", 100, 25, 15);

        assertEquals("Infantry", unit.getName());
        assertEquals(100, unit.getHealth());
        assertEquals(25, unit.getAttack());
        assertEquals(15, unit.getArmor());
        assertEquals(2, unit.getAttackBonus());
        assertEquals(1, unit.getResistBonus());
        assertEquals(0, unit.getHillModifier());
        assertEquals(0, unit.getPlainsModifier());
        assertEquals(4, unit.getForestModifier());
    }

    @Test
    public void testPredefinedInfantryUnitCreation() {
        InfantryUnit unit = new InfantryUnit("Infantry", 100);

        assertEquals(15, unit.getAttack());
        assertEquals(10, unit.getArmor());
    }
}
