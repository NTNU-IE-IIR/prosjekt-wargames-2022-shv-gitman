package org.ntnu.wargames;

public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;

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
     * @param opponent The unit that is attacked.
     */
    public void attack(Unit opponent) {
        opponent.setHealth(
                opponent.getHealth() -
                (this.attack + this.getAttackBonus()) +
                (opponent.getArmor() + opponent.getResistBonus()));
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
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }

    /**
     * Returns attack bonus of the unit.
     * @return The attack bonus of the unit.
     */
    public abstract int getAttackBonus();

    /**
     * Returns attack bonus of the unit.
     * @return The attack bonus of the unit.
     */
    public abstract int getResistBonus();
}
