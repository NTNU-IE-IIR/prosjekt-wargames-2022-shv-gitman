package no.ntnu.idatx2001.wargames.model.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArtilleryUnitTest {

    @Test
    public void testArtilleryUnitCreation() {
        ArtilleryUnit unit = new ArtilleryUnit("Artillery", 180, 20, 12);

        assertEquals("Artillery", unit.getName());
        assertEquals(180, unit.getHealth());
        assertEquals(20, unit.getAttack());
        assertEquals(12, unit.getArmor());
        assertEquals(4, unit.getAttackBonus());
        assertEquals(1, unit.getResistBonus());
        assertEquals(-2, unit.getHillModifier());
        assertEquals(4, unit.getPlainsModifier());
        assertEquals(-unit.getAttackBonus(), unit.getForestModifier());
    }

    @Test
    public void testPredefinedArtilleryUnitCreation() {
        ArtilleryUnit unit = new ArtilleryUnit("Artillery", 180);

        assertEquals(3, unit.getAttack());
        assertEquals(8, unit.getArmor());
    }
}
