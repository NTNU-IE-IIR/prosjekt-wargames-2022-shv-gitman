package no.ntnu.idatx2001.wargames.model.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CavalryUnitTest {

    @Test
    public void testCavalryUnitCreation() {
        CavalryUnit unit = new CavalryUnit("Cavalry", 180, 20, 12);

        assertEquals("Cavalry", unit.getName());
        assertEquals(180, unit.getHealth());
        assertEquals(20, unit.getAttack());
        assertEquals(12, unit.getArmor());
        assertEquals(2, unit.getAttackBonus());
        assertEquals(1, unit.getResistBonus());
        assertEquals(0, unit.getHillModifier());
        assertEquals(6, unit.getPlainsModifier());
        assertEquals(-unit.getResistBonus(), unit.getForestModifier());
    }

    @Test
    public void testPredefinedCavalryUnitCreation() {
        CavalryUnit unit = new CavalryUnit("Cavalry", 180);

        assertEquals(20, unit.getAttack());
        assertEquals(12, unit.getArmor());
    }
}
