package no.ntnu.idatx2001.wargames.model.units;

/**
 * Represents the base of a unit.
 */
public abstract class Unit {
  private final String name;
  private final int attack;
  private final int armor;
  private int health;
  private String terrain;

  // For simulating the additional attack bonus
  private int unitAttackTurn = 0;
  private int unitDefenceTurn = 0;

  // Terrain constants
  private final String FOREST = "FOREST";
  private final String HILL = "HILL";
  private final String PLAINS = "PLAINS";

  /**
   * Creates a unit with a set of stats.
   *
   * @param name   The name of the unit.
   * @param health The health of the unit.
   * @param attack The attack of the unit.
   * @param armor  The armor of the unit.
   */
  protected Unit(String name, int health, int attack, int armor) {
    this.name = name;
    this.health = Math.max(health, 0);
    this.attack = Math.max(attack, 0);
    this.armor = Math.max(armor, 0);
    this.terrain = "";
  }

  /**
   * Attacks an opponent.
   * The damage the opponent takes is determined by the attacker and opponents base attack/defence,
   * attack/defence bonus and terrain modifier.
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
    // InfantryUnit gets bonus when attacking in FOREST
    if (this instanceof InfantryUnit) {
      attackBonus = applyModifier(this, attackBonus);
    }

    // RangedUnit gets a positive modifier when attacking in a HILL
    // and a negative modifier when attacking in a FOREST
    else if (this instanceof RangedUnit) {
      attackBonus = applyModifier(this, attackBonus);
    }

    // CavalryUnit & CommanderUnit gets a bonus when attacking in PLAINS.
    else if (this instanceof CavalryUnit) {
      if (unitAttackTurn == 0) {
        attackBonus = attackBonus + 4;
      }
      if (this.getTerrain().equals(HILL)) {
        attackBonus = applyModifier(this, attackBonus);
      }
    }

    // ArtilleryUnit get a positive bonus when attacking in PLAINS
    // and a negative modifier when attacking in a FOREST
    else if (this instanceof ArtilleryUnit) {
      if (unitAttackTurn == 0) {
        attackBonus = attackBonus + 26;
      }
      if (this.getTerrain().equals(PLAINS) || this.getTerrain().equals(FOREST)) {
        attackBonus = applyModifier(this, attackBonus);
      }
    }

    // Gives additional defence-modifiers.
    // InfantryUnit gets bonus when defending from in FOREST
    else if (opponent instanceof InfantryUnit) {
      resistBonus = applyModifier(opponent, resistBonus);
    }

    // RangedUnit gets an additional +4 bonus at first attack
    // and a +2 bonus at second attack
    if (opponent instanceof RangedUnit) {
      if (opponent.getUnitDefenceTurn() == 0) {
        resistBonus = resistBonus + 4;
      } else if (opponent.getUnitDefenceTurn() == 1) {
        resistBonus = resistBonus + 2;
      }
    }

    // When CavalryUnit gets attacked in a FOREST, all resistance bonus is removed.
    else if (opponent instanceof CavalryUnit && terrain.equals(FOREST)) {
      resistBonus = 0;
    }

    // ArtilleryUnit doesn't take damage at first defence.
    // ArtilleryUnit gets a negative modifier when defending in a HILL
    else if (opponent instanceof ArtilleryUnit) {
      if (opponent.getUnitDefenceTurn() == 0) {
        resistBonus = resistBonus + attackBonus + this.attack;
      }
      if (terrain.equals(HILL)) {
        resistBonus = applyModifier(opponent, resistBonus);
      }
    }

    this.increaseUnitAttackTurn();
    opponent.increaseUnitDefenceTurn();

    // Only attacks if the attack is higher than opponents defence
    if ((this.attack + attackBonus) > (opponent.getArmor() + resistBonus)) {
      opponent.setHealth(
          opponent.getHealth() - (this.attack + attackBonus) + (opponent.getArmor() + resistBonus)
      );
    }
  }

  /**
   * Applies terrain modifiers to the defence/attack bonus.
   *
   * @param unit unit to get terrain modifier from
   * @param currentModifier current modifier
   * @return bonus value with modifier
   */
  private int applyModifier(Unit unit, int currentModifier) {
    return switch (this.terrain) {
      case HILL -> currentModifier + unit.getHillModifier();
      case FOREST -> currentModifier + unit.getForestModifier();
      case PLAINS -> currentModifier + unit.getPlainsModifier();
      default -> 0;
    };
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
