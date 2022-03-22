package org.ntnu.wargames;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BattleTest {

  @Test
  public void test_Battle_Simulation() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 1000);
    InfantryUnit goblinUnit = new InfantryUnit("Goblin", 10);

    Army testArmyOne = new Army("testArmyOne");
    Army testArmyTwo = new Army("testArmyTwo");

    testArmyOne.add(infantryUnit);
    testArmyTwo.add(goblinUnit);

    Battle battle = new Battle(testArmyOne, testArmyTwo);

    Army winner = battle.simulate();

    if (testArmyOne.hasUnits()) {
      assertEquals(winner, testArmyOne);
    } else {
      assertEquals(winner, testArmyTwo);
    }
  }

  @Test
  public void test_Battle_Simulation_With_Army_From_Text_file() {
    Army orcArmy = Army.uploadArmyFromFile("orc-army.csv");
    Army humanArmy = Army.uploadArmyFromFile("human-army.csv");

    Battle battle = new Battle(orcArmy, humanArmy);

    Army winner = battle.simulate();

    winner.printAllUnits();

    if (orcArmy.hasUnits()) {
      assertEquals(winner, orcArmy);
    } else {
      assertEquals(winner, humanArmy);
    }
  }
}
