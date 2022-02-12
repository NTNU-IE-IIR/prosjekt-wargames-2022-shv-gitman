package org.ntnu.wargames;

public class CommanderUnit extends CavalryUnit{
    /**
     * Creates a commander unit.
     * @param name The name of the unit.
     * @param health The health of the unit.
     * @param attack The attack of the unit.
     * @param armor The armor of the unit.
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name,health,attack,armor);
    }

    /**
     * Creates a commander unit with a predefined
     * attack and armor value.
     * @param name The name of the unit.
     * @param health The health of the unit.
     */
    public CommanderUnit(String name, int health) {
        super(name, health, 25, 15);
    }
}
