package org.ntnu.wargames;

/**
 * Represents a cavalry unit.
 */
public class CavalryUnit extends Unit {

  /**
   * Creates a cavalry unit.
   *
   * @param name   The name of the unit.
   * @param health The health of the unit.
   * @param attack The attack of the unit.
   * @param armor  The armor of the unit.
   */
  public CavalryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Creates a cavalry unit with a predefined
   * attack and armor value.
   *
   * @param name   The name of the unit.
   * @param health The health of the unit.
   */
  public CavalryUnit(String name, int health) {
    super(name, health, 20, 12);
  }

  /**
   * Returns the default attack bonus for the unit.
   *
   * @return the default attack bonus for the unit.
   */
  @Override
  public int getAttackBonus() {
    return 2;
  }

  /**
   * Returns the default resistance bonus for the unit.
   *
   * @return the default resistance bonus for the unit.
   */
  @Override
  public int getResistBonus() {
    return 1;
  }
}
