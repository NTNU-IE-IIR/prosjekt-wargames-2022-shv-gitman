package no.ntnu.idatx2001.wargames;

import org.junit.Test;
import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.Battle;
import no.ntnu.idatx2001.wargames.model.units.InfantryUnit;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class BattleTest {

  @Test
  public void testBattleSimulation() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 1000);
    InfantryUnit goblinUnit = new InfantryUnit("Goblin", 10);

    Army testArmyOne = new Army("testArmyOne");
    Army testArmyTwo = new Army("testArmyTwo");

    testArmyOne.add(infantryUnit);
    testArmyTwo.add(goblinUnit);

    Battle battle = new Battle(testArmyOne, testArmyTwo, "PLAINS");

    Army winner = battle.simulate();

    if (testArmyOne.hasUnits()) {
      assertEquals(winner, testArmyOne);
    } else {
      assertEquals(winner, testArmyTwo);
    }
  }

  @Test
  public void testBattleSimulationWithArmyFromTextFile() {
    Army orcArmy = Army.uploadArmyFromFile("army-templates/Orc Army.csv");
    Army humanArmy = Army.uploadArmyFromFile("army-templates/Human army.csv");

    Battle battle = new Battle(orcArmy, humanArmy, "PLAINS");

    Army winner = battle.simulate();

    winner.printAllUnits();

    if (Objects.requireNonNull(orcArmy).hasUnits()) {
      assertEquals(winner, orcArmy);
    } else {
      assertEquals(winner, humanArmy);
    }
  }
}
