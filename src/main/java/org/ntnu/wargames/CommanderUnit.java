package org.ntnu.wargames;

/**
 * Represents a Commander unit of the type CavalryUnit.
 */
public class CommanderUnit extends CavalryUnit {
  /**
   * Creates a commander unit.
   *
   * @param name   The name of the commander unit.
   * @param health The health of the commander unit.
   * @param attack The attack of the commander unit.
   * @param armor  The armor of the commander unit.
   */
  public CommanderUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Creates a commander unit with a predefined
   * attack and armor value.
   *
   * @param name   The name of the commander unit.
   * @param health The health of the commander unit.
   */
  public CommanderUnit(String name, int health) {
    super(name, health, 25, 14);
  }
}
