package no.ntnu.idatx2001.wargames.model.units;

/**
 * Represents the base of a unit.
 */
public abstract class Unit {
  private final String name;
  private int health;
  private final int attack;
  private final int armor;
  private String terrain;

  // For simulating the additional attack bonus
  private int unitAttackTurn = 0;
  private int unitDefenceTurn = 0;

  /**
   * Creates a unit with a set of stats.
   *
   * @param name   The name of the unit.
   * @param health The health of the unit.
   * @param attack The attack of the unit.
   * @param armor  The armor of the unit.
   */
  public Unit(String name, int health, int attack, int armor) {
    this.name = name;
    this.health = Math.max(health, 0);
    this.attack = Math.max(attack, 0);
    this.armor = Math.max(armor, 0);
    this.terrain = "";
  }

  /**
   * Attacks an opponent.
   * The attack is determined by the units attack and attack bonus stat,
   * and reduced by the opponents armor and resist bonus stat.
   * Checks if an attacker has attacked before, or
   * defender has defended before, and adds extra
   * attack or resistance bonus.
   *
   * @param opponent The unit that is attacked.
   */
  public void attack(Unit opponent) {
    int attackBonus = this.getAttackBonus();
    int resistBonus = opponent.getResistBonus();

    // Gives additional attack-modifiers.
    if (this instanceof InfantryUnit && terrain.equals("FOREST")) {
      attackBonus = attackBonus + this.getForestModifier();
    } else if (this instanceof RangedUnit) {
      if (terrain.equals("HILL")) {
        attackBonus = attackBonus + this.getHillModifier();
      } else if (terrain.equals("FOREST")) {
        attackBonus = attackBonus + this.getForestModifier();
      }
    } else if (this instanceof CavalryUnit) {
      if (unitAttackTurn == 0) {
        attackBonus = attackBonus + 4;
      }
      if (terrain.equals("PLAINS")) {
        attackBonus = attackBonus + this.getPlainsModifier();
      }
    } else if (this instanceof ArtilleryUnit) {
      if (unitAttackTurn == 0) {
        attackBonus = attackBonus + 26;
      }
      if (terrain.equals("PLAINS")) {
        attackBonus = attackBonus + this.getPlainsModifier();
      }
    }

    // Gives additional defence-modifiers.
    if (opponent instanceof RangedUnit) {
      if (opponent.getUnitDefenceTurn() == 0) {
        resistBonus = resistBonus + 4;
      } else if (opponent.getUnitDefenceTurn() == 1) {
        resistBonus = resistBonus + 2;
      }
      if (terrain.equals("HILL")) {
        resistBonus = resistBonus + opponent.getHillModifier();
      }
    } else if (opponent instanceof InfantryUnit && terrain.equals("FOREST")) {
      resistBonus = resistBonus + opponent.getForestModifier();
    } else if (opponent instanceof CavalryUnit && terrain.equals("FOREST")) {
      resistBonus = resistBonus + opponent.getForestModifier();
    } else if (opponent instanceof ArtilleryUnit) {
      if (opponent.getUnitDefenceTurn() == 0 && !(terrain.equals("HILL"))) {
        resistBonus = resistBonus + attackBonus + this.attack;
      }
      if (terrain.equals("HILL")) {
        resistBonus = resistBonus + opponent.getHillModifier();
      }
    }

    this.increaseUnitAttackTurn();
    opponent.increaseUnitDefenceTurn();

    // Only attacks if the attack is higher than opponents defence
    if ((this.attack + attackBonus) > (opponent.getArmor() + resistBonus)) {
      opponent.setHealth(opponent.getHealth() - (this.attack + attackBonus) + (opponent.getArmor() + resistBonus));
    } else {
      // System.out.println(
      // getName() + " doesnt have enough attack (" + (attack + attackBonus) + ") to do anything against "
      // + opponent.getName() + " (" + (opponent.getArmor() + opponent.getResistBonus()) + ")"
      // );
    }
  }

  /**
   * Returns the name of the unit.
   *
   * @return the name of the unit.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the health of the unit.
   *
   * @return the health of the unit.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Returns the attack of the unit.
   *
   * @return the attack of the unit.
   */
  public int getAttack() {
    return attack;
  }

  /**
   * Returns the armor of the unit.
   *
   * @return the armor of the unit.
   */
  public int getArmor() {
    return armor;
  }

  /**
   * Sets a new health value of the unit.
   *
   * @param health the new health value of the unit.
   */
  public void setHealth(int health) {
    this.health = health;
  }

  /**
   * Sets the terrain the Unit is on.
   *
   * @param terrain terrain to put Unit on
   */
  public void setTerrain(String terrain) {
    this.terrain = terrain;
  }

  /**
   * Returns terrain unit is on.
   *
   * @return terrain unit is on
   */
  public String getTerrain() {
    return terrain;
  }

  /**
   * Returns a string of the name, health, attack and armor
   * values of the unit.
   *
   * @return A string representing the unit.
   */
  @Override
  public String toString() {
    return "Unit: " + "name = " + name + ", health = " + health
        + ", attack = " + attack + ", armor = " + armor;
  }

  /**
   * Returns default attack bonus of the unit.
   *
   * @return The default attack bonus of the unit.
   */
  public abstract int getAttackBonus();

  /**
   * Returns default resist bonus of the unit.
   *
   * @return The resist bonus of the unit.
   */
  public abstract int getResistBonus();

  /**
   * Returns hill modifier of the unit.
   *
   * @return hill modifier of the unit
   */
  public abstract int getHillModifier();

  /**
   * Returns plains modifier of the unit.
   *
   * @return plains modifier of the unit
   */
  public abstract int getPlainsModifier();

  /**
   * Returns forest modifier of the unit.
   *
   * @return forest modifier of the unit
   */
  public abstract int getForestModifier();

  /**
   * Returns the amount of times a unit has attacked.
   *
   * @return the amount of times a unit has attacked.
   */
  public int getUnitAttackTurn() {
    return unitAttackTurn;
  }

  /**
   * Returns the amount of times a unit has defended.
   *
   * @return the amount of times a unit has defended.
   */
  public int getUnitDefenceTurn() {
    return unitDefenceTurn;
  }

  /**
   * Increases the amount of times a unit has attacked.
   */
  public void increaseUnitAttackTurn() {
    unitAttackTurn++;
  }

  /**
   * Increases the amount of times a unit has defended.
   */
  public void increaseUnitDefenceTurn() {
    unitDefenceTurn++;
  }
}
