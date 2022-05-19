package no.ntnu.idatx2001.wargames.model.units;

/**
 * Represents a ranged unit.
 */
public class RangedUnit extends Unit {

  /**
   * Creates a ranged unit.
   *
   * @param name   The name of the ranged unit.
   * @param health The health of the ranged unit.
   * @param attack The attack of the ranged unit.
   * @param armor  The armor of the ranged unit.
   */
  public RangedUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Creates a ranged unit with a predefined
   * attack and armor value.
   *
   * @param name   The name of the unit.
   * @param health The health of the unit.
   */
  public RangedUnit(String name, int health) {
    super(name, health, 15, 8);
  }

  /**
   * Returns the default attack bonus for the ranged unit.
   *
   * @return the default attack bonus for the ranged unit.
   */
  @Override
  public int getAttackBonus() {
    return 3;
  }

  /**
   * Returns the default resistance bonus for the ranged unit.
   * (In attack method: +4 bonus in first attack, +2 in second)
   *
   * @return the default resistance bonus for the ranged unit.
   */
  @Override
  public int getResistBonus() {
    return 2;
  }

  @Override
  public int getHillModifier() {
    return 2;
  }

  @Override
  public int getPlainsModifier() {
    return 0;
  }

  @Override
  public int getForestModifier() {
    return -2;
  }
}
