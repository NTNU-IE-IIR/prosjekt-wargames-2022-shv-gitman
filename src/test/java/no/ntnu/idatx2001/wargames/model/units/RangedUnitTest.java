package no.ntnu.idatx2001.wargames.model.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangedUnitTest {

    @Test
    public void testRangedUnitCreation() {
        RangedUnit unit = new RangedUnit("Ranged", 100, 15, 8);

        assertEquals("Ranged", unit.getName());
        assertEquals(100, unit.getHealth());
        assertEquals(15, unit.getAttack());
        assertEquals(8, unit.getArmor());
        assertEquals(3, unit.getAttackBonus());
        assertEquals(2, unit.getResistBonus());
        assertEquals(2, unit.getHillModifier());
        assertEquals(0, unit.getPlainsModifier());
        assertEquals(-2, unit.getForestModifier());
    }

    @Test
    public void testPredefinedRangedUnitCreation() {
        RangedUnit unit = new RangedUnit("Ranged", 100);

        assertEquals(15, unit.getAttack());
        assertEquals(8, unit.getArmor());
    }
}
