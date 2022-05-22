package no.ntnu.idatx2001.wargames;

import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.Battle;
import no.ntnu.idatx2001.wargames.model.units.CavalryUnit;
import no.ntnu.idatx2001.wargames.model.units.CommanderUnit;
import no.ntnu.idatx2001.wargames.model.units.InfantryUnit;
import no.ntnu.idatx2001.wargames.model.units.RangedUnit;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

  @Test
  public void testInfantryUnitCreation() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100, 25, 15);

    assertEquals("Infantry", infantryUnit.getName());
    assertEquals(100, infantryUnit.getHealth());
    assertEquals(25, infantryUnit.getAttack());
    assertEquals(15, infantryUnit.getArmor());
    assertEquals(2, infantryUnit.getAttackBonus());
    assertEquals(1, infantryUnit.getResistBonus());
  }

  @Test
  public void testPredefinedInfantryUnitCreation() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);

    assertEquals(15, infantryUnit.getAttack());
    assertEquals(10, infantryUnit.getArmor());
  }

  @Test
  public void testCommanderUnitCreation() {
    CommanderUnit commanderUnit = new CommanderUnit("Commander", 180, 50, 20);

    assertEquals("Commander", commanderUnit.getName());
    assertEquals(180, commanderUnit.getHealth());
    assertEquals(50, commanderUnit.getAttack());
    assertEquals(20, commanderUnit.getArmor());
    assertEquals(2, commanderUnit.getAttackBonus());
    assertEquals(1, commanderUnit.getResistBonus());
  }

  @Test
  public void testPredefinedCommanderUnitCreation() {
    CommanderUnit commanderUnit = new CommanderUnit("Commander", 180);

    assertEquals(25, commanderUnit.getAttack());
    assertEquals(15, commanderUnit.getArmor());
  }

  @Test
  public void testFirstAttackCavalryInfantry() {
    CavalryUnit cavalryUnit = new CavalryUnit("Cavalry", 100);
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);

    cavalryUnit.attack(infantryUnit);

    // Cavalry attack: 20 + 6 (first attack bonus)
    // Infantry defence: 10 + 1 (resist bonus)
    assertEquals(85, infantryUnit.getHealth());
  }

  @Test
  public void testSecondAttackCavalryInfantry() {
    CavalryUnit cavalryUnit = new CavalryUnit("Cavalry", 100);
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);

    // First attack
    // Cavalry attack: 20 + 6 (first attack bonus)
    // Infantry defence: 10 + 1 (resist bonus)
    // expected: 100 - 26 + 11 = 85
    cavalryUnit.attack(infantryUnit);

    // Second attack
    // Cavalry attack: 20 + 2 (second attack bonus)
    // Infantry defence: 10 + 1 (resist bonus)
    // expected: 85 - 22 + 11 = 74
    cavalryUnit.attack(infantryUnit);

    assertEquals(74, infantryUnit.getHealth());
  }

  @Test
  public void testRangedUnitResistanceBonus() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
    RangedUnit rangedUnit = new RangedUnit("Archer", 100);

    // First attack
    // Infantry attack: 15 + 2 (attack bonus)
    // Archer defence: 8 + 6 (resist bonus)
    // expected: 100 - 17 + 14 = 97
    infantryUnit.attack(rangedUnit);

    // Second attack
    // Infantry attack: 15 + 2 (attack bonus)
    // Archer defence: 8 + 4 (resist bonus)
    // expected: 100 - 17 + 12 = 92
    infantryUnit.attack(rangedUnit);

    // Third attack
    // Infantry attack: 15 + 2 (attack bonus)
    // Archer defence: 8 + 2 (resist bonus)
    // expected: 100 - 17 + 10 = 85
    infantryUnit.attack(rangedUnit);

    assertEquals(85, rangedUnit.getHealth());
  }

  @Test
  public void testCavalryResistanceBonusInForest() {
    RangedUnit rangedUnit = new RangedUnit("Archer", 100);
    rangedUnit.setTerrain("FOREST");
    CavalryUnit cavalryUnit = new CavalryUnit("Cavalry", 100);
    cavalryUnit.setTerrain("FOREST");

    Army armyOne = new Army("Test Attackers");
    armyOne.add(rangedUnit);
    Army armyTwo = new Army("Test Defenders");
    armyTwo.add(cavalryUnit);

    // Ranged attack: 15 + 3 - 2 = 16
    // Cavalry defence: 12 + 1 - 1 = 12
    // Expected health = 96
    rangedUnit.attack(cavalryUnit);

    assertEquals(96, cavalryUnit.getHealth());
  }
}
