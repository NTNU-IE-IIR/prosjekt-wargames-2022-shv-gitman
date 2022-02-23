package org.ntnu.wargames;

public class RangedUnit extends Unit{

    /**
     * Creates a ranged unit.
     * @param name The name of the unit.
     * @param health The health of the unit.
     * @param attack The attack of the unit.
     * @param armor The armor of the unit.
     */
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name,health,attack,armor);
    }

    /**
     * Creates a ranged unit with a predefined
     * attack value of 15, and armor value of 8.
     * @param name The name of the unit.
     * @param health The health of the unit.
     */
    public RangedUnit(String name, int health) {
        super(name, health, 15, 8);
    }

    /**
     * Returns the default attack bonus for the unit.
     * @return the default attack bonus for the unit.
     */
    @Override
    public int getAttackBonus() {
        return 3;
    }

    /**
     * Returns the default resistance bonus for the unit.
     * @return the default resistance bonus for the unit.
     */
    @Override
    public int getResistBonus() {
        return 2;
    }
}
