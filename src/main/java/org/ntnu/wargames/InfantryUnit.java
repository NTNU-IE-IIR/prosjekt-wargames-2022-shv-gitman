package org.ntnu.wargames;

public class InfantryUnit extends Unit {

    /**
     * Creates an infantry unit.
     * @param name The name of the unit.
     * @param health The health of the unit.
     * @param attack The attack of the unit.
     * @param armor The armor of the unit.
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name,health,attack,armor);
    }

    /**
     * Creates an infantry unit with a predefined
     * attack and armor value.
     * @param name The name of the unit.
     * @param health The health of the unit.
     */
    public InfantryUnit(String name, int health) {
        super(name, health, 15, 10);
    }

    /**
     * Returns the default attack bonus for the unit.
     * @return the default attack bonus for the unit.
     */
    @Override
    public int getAttackBonus() {
        return 2;
    }

    /**
     * Returns the default resistance bonus for the unit.
     * @return the default resistance bonus for the unit.
     */
    @Override
    public int getResistBonus() {
        return 1;
    }
}
