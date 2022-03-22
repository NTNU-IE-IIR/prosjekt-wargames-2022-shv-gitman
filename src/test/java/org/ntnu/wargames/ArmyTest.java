package org.ntnu.wargames;

import org.junit.Test;

import static org.junit.Assert.*;

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
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
    Army army = new Army("Test army");

    army.add(infantryUnit);

    assertTrue(army.hasUnits());
  }

  @Test
  public void test_Get_Infantry_Units() {
    Unit infantryUnit1 = new InfantryUnit("Infantry1", 100);
    Unit infantryUnit2 = new InfantryUnit("Infantry2", 100);
    Unit cavalryUnit = new CavalryUnit("Cavalry", 100);
    Unit rangedUnit = new RangedUnit("Ranged", 100);
    Unit commanderUnit1 = new CommanderUnit("CommanderUnit1", 180);
    Unit commanderUnit2 = new CommanderUnit("CommanderUnit2", 180);
    Army army = new Army("Test army");

    army.add(infantryUnit1);
    army.add(infantryUnit2);
    army.add(cavalryUnit);
    army.add(rangedUnit);
    army.add(commanderUnit1);
    army.add(commanderUnit2);

    assertEquals(army.getInfantryUnits().size(),2);
    assertEquals(army.getCavalryUnits().size(),1);
    assertEquals(army.getRangedUnits().size(),1);
    assertEquals(army.getCommanderUnits().size(),2);
  }
}
