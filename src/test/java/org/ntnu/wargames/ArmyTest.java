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

  @Test
  public void test_Orc_Army_Creation_From_File() {
    Army army = Army.uploadArmyFromFile("orc-army.csv");

    assertEquals(army.getAmountOfUnits(), 16);
    assertTrue(army.hasUnits());
  }

  @Test
  public void test_Human_Army_Creation_From_File() {
    Army army = Army.uploadArmyFromFile("human-army.csv");

    assertEquals(army.getAmountOfUnits(), 16);
    assertTrue(army.hasUnits());
  }

  @Test
  public void test_Save_Army_To_File() {
    Army army = new Army("Test army");

    army.add(new CommanderUnit("Mountain King", 180));

    for (int i = 1; i <= 10; i++) {
      army.add(new InfantryUnit("Footman", 100));
    }

    for (int i = 1; i <= 10; i++) {
      army.add(new RangedUnit("Archer", 100));
    }

    for (int i = 1; i <= 10; i++) {
      army.add(new CavalryUnit("Cavalry", 100));
    }

    army.saveArmyToFile("test.csv");
  }
}
