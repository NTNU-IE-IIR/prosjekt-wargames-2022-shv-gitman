package org.ntnu.wargames;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArmyTest {

    @Test
    public void test_Remove_Unit_From_Army() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
        Army army = new Army("Test army");

        army.add(infantryUnit);
        army.remove(infantryUnit);

        assertFalse(army.hasUnits());
    }

    @Test
    public void test_Add_Unit_To_Army() {
        InfantryUnit infantryUnit = new InfantryUnit("Infantry",100);
        Army army = new Army("Test army");

        army.add(infantryUnit);

        assertTrue(army.hasUnits());
    }
}
