package org.ntnu.wargames;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BattleTest {

  @Test
  public void test_Battle_Simulation() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
    InfantryUnit goblinUnit = new InfantryUnit("Goblin", 10);

    Army testArmyOne = new Army("testArmyOne");
    Army testArmyTwo = new Army("testArmyTwo");

    testArmyOne.add(infantryUnit);
    testArmyTwo.add(goblinUnit);

    Battle battle = new Battle(testArmyOne, testArmyTwo);

    Army winner = battle.simulate();

    assertEquals(winner, testArmyOne);
  }
}
