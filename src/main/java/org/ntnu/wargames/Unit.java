package org.ntnu.wargames;

public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;

    // For simulering
    private int unitAttackTurn = 0;
    private int unitDefenceTurn = 0;

    /**
     * Creates a unit with a set of stats.
     * @param name The name of the unit.
     * @param health The health of the unit.
     * @param attack The attack of the unit.
     * @param armor The armor of the unit.
     */
    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Attacks an opponent.
     * The attack is determined by the units attack and attack bonus stat,
     * and reduced by the opponents armor and resist bonus stat.
     *
     * Checks if an attacker has attacked before, or
     * defender has defended before, and adds extra
     * attack or resistance bonus.
     *
     * @param opponent The unit that is attacked.
     */
    public void attack(Unit opponent) {
        int attackBonus = this.getAttackBonus();
        int resistBonus = opponent.getResistBonus();

        // Checks if its CavalryUnits first attack and adds extra attackbonus.
        if (this instanceof CavalryUnit && unitAttackTurn == 0) {
            attackBonus = attackBonus + 4;
        }

        // Checks if Ranged unit has been attacked before and adds resistance bonus.
        if (opponent instanceof RangedUnit) {
            if (opponent.getUnitDefenceTurn() == 0) {
                resistBonus = resistBonus + 4;
            } else if (opponent.getUnitDefenceTurn() == 1) {
                resistBonus = resistBonus + 2;
            }
        }

        this.increaseUnitAttackTurn();
        opponent.increaseUnitDefenceTurn();

        // TODO: checks if attack is higher than defence.

        // Sets opponents new health.
        opponent.setHealth(
                opponent.getHealth() -
                (this.attack + attackBonus) +
                (opponent.getArmor() + resistBonus));
    }

    /**
     * Returns the name of the unit.
     * @return the name of the unit.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the health of the unit.
     * @return the health of the unit.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the attack of the unit.
     * @return the attack of the unit.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns the armor of the unit.
     * @return the armor of the unit.
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Sets a new health value of the unit.
     * @param health the new health value of the unit.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Returns a string of the name, health, attack and armor
     * values of the unit.
     * @return A string representing the unit.
     */
    @Override
    public String toString() {
        return "Unit: " +
                "name = " + name +
                ", health = " + health +
                ", attack = " + attack +
                ", armor = " + armor;
    }

    /**
     * Returns default attack bonus of the unit.
     * @return The default attack bonus of the unit.
     */
    public abstract int getAttackBonus();

    /**
     * Returns default resist bonus of the unit.
     * @return The resist bonus of the unit.
     */
    public abstract int getResistBonus();

    /**
     * Returns the amount of times a unit has attacked.
     * @return the amount of times a unit has attacked.
     */
    public int getUnitAttackTurn() {
        return unitAttackTurn;
    }

    /**
     * Returns the amount of times a unit has defended.
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
