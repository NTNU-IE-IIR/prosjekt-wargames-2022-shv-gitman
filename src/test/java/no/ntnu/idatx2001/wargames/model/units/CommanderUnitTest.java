package no.ntnu.idatx2001.wargames.model.units;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommanderUnitTest {

    @Test
    public void testCommanderUnitCreation() {
        CommanderUnit unit = new CommanderUnit("Commander", 180, 50, 20);

        assertEquals("Commander", unit.getName());
        assertEquals(180, unit.getHealth());
        assertEquals(50, unit.getAttack());
        assertEquals(20, unit.getArmor());
        assertEquals(2, unit.getAttackBonus());
        assertEquals(1, unit.getResistBonus());
        assertEquals(0, unit.getHillModifier());
        assertEquals(6, unit.getPlainsModifier());
        assertEquals(-unit.getResistBonus(), unit.getForestModifier());
    }

    @Test
    public void testPredefinedCommanderUnitCreation() {
        CommanderUnit unit = new CommanderUnit("Commander", 180);

        assertEquals(25, unit.getAttack());
        assertEquals(14, unit.getArmor());
    }
}
