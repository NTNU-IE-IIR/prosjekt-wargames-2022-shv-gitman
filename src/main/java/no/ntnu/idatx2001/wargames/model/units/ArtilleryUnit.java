package no.ntnu.idatx2001.wargames.model.units;

public class ArtilleryUnit extends Unit {

  /**
   * Creates an artillery unit.
   * The artillery unit does a lot of damage
   *
   * @param name   The name of the artillery unit.
   * @param health The health of the artillery unit.
   * @param attack The attack of the artillery unit.
   * @param armor  The armor of the artillery unit.
   */
  public ArtilleryUnit(String name, int health, int attack, int armor) {
    super(name, health, attack, armor);
  }

  /**
   * Creates an artillery unit with a predefined
   * attack and armor value.
   *
   * @param name   The name of the unit.
   * @param health The health of the unit.
   */
  public ArtilleryUnit(String name, int health) {
    super(name, health, 3, 8);
  }

  /**
   * Returns the default attack bonus for the artillery unit.
   * (In attack method: +26 bonus in first attack)
   *
   * @return the default attack bonus for the artillery unit.
   */
  @Override
  public int getAttackBonus() {
    return 4;
  }

  /**
   * Returns the default resistance bonus for the artillery unit.
   * (In attack method: Takes no damage first time someone attacks)
   *
   * @return the default resistance bonus for the artillery unit.
   */
  @Override
  public int getResistBonus() {
    return 1;
  }

  /**
   * Returns the hill modifier.
   *
   * @return hill modifier
   */
  public int getHillModifier() {
    return -2;
  }

  /**
   * Returns the plains modifier.
   *
   * @return plains modifier
   */
  @Override
  public int getPlainsModifier() {
    return 12;
  }

  /**
   * Returns the forest modifier.
   * Removes all base attack bonus when in a forest.
   *
   * @return the negative of base attack bonus.
   */
  @Override
  public int getForestModifier() {
    return -getAttackBonus();
  }
}
