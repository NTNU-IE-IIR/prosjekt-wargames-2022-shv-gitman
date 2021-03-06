package no.ntnu.idatx2001.wargames.model;

import no.ntnu.idatx2001.wargames.model.units.Unit;

/**
 * A simulation of a battle between two armies.
 */
public class Battle {
  private final Army armyOne;
  private final Army armyTwo;
  private String terrain;

  /**
   * Creates a simulation of a battle between
   * two armies.
   *
   * @param armyOne the first army in the battle.
   * @param armyTwo the second army in the battle.
   * @param terrain the terrain of the battle.
   */
  public Battle(Army armyOne, Army armyTwo, String terrain) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    setTerrain(terrain);
  }

  /**
   * Simulates a battle between two armies.
   *
   * @return the winner of the battle with the remaining units.
   */
  public Army simulate() {
    int turn = 0;

    // Changes attacker each turn
    while (armyOne.hasUnits() && armyTwo.hasUnits()) {
      if (turn % 2 == 0) {
        simulateOneTurn(armyOne, armyTwo);
      } else {
        simulateOneTurn(armyTwo, armyOne);
      }
      turn++;
    }

    if (armyTwo.hasUnits()) {
      return armyTwo;
    } else {
      return armyOne;
    }
  }

  /**
   * Simulates one turn in a battle.
   *
   * @param attackers attackers of battle
   * @param defenders defenders of battle turn
   */
  public void simulateOneTurn(Army attackers, Army defenders) {
    Unit attackerUnit = attackers.getRandom();
    Unit defenderUnit = defenders.getRandom();

    attackerUnit.attack(defenderUnit);

    // If health of defender gets below 0, it dies.
    if (defenderUnit.getHealth() <= 0) {
      defenders.remove(defenderUnit);
    }

    new Battle(attackers, defenders, this.terrain);
  }

  /**
   * Sets terrain for the battle.
   *
   * @param terrain terrain to set.
   */
  public void setTerrain(String terrain) {
    this.terrain = terrain;
    for (Unit unit : armyOne.getAllUnits()) {
      unit.setTerrain(terrain);
    }
    for (Unit unit : armyTwo.getAllUnits()) {
      unit.setTerrain(terrain);
    }
  }

  /**
   * Returns the terrain for the battle.
   *
   * @return the terrain for the battle.
   */
  public String getTerrain() {
    return terrain;
  }

  /**
   * Returns a string containing information of a battle.
   *
   * @return a string containing information of a battle.
   */
  @Override
  public String toString() {
    return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
  }
}
