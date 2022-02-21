package org.ntnu.wargames;

public class Battle {
    private Army armyOne;
    private Army armyTwo;

    /**
     * Creates a simulation of a battle between
     * two armies.
     * @param armyOne the first army in the battle.
     * @param armyTwo the second army in the battle.
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    public Battle simulate() {
        addArmy_One();
        addArmy_Two();
        Battle battle = new Battle(this.armyOne, this.armyTwo);
        return battle;
    }

    /**
     * Returns a string containing information of a battle.
     * @return a string containing information of a battle.
     */
    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }

    /**
     * Adds a human army for testing
     */
    private void addArmy_One() {
        // Adds the commander
        armyOne.add(new CommanderUnit("Mountain King", 180));

        // Adds the infantry
        for (int i = 1; i <= 500; i++) {
            armyOne.add(new InfantryUnit("Footman", 100));
        }

        // Adds the ranged units
        for (int i = 1; i <= 200; i++) {
            armyOne.add(new RangedUnit("Archer", 100));
        }

        // Adds the cavalry units
        for (int i = 1; i <= 100; i++) {
            armyOne.add(new CavalryUnit("Cavalry", 100));
        }
    }

    /**
     * Adds an orc army for testing
     */
    private void addArmy_Two() {
        // Adds the commander
        armyTwo.add(new CommanderUnit("Gul'dan", 180));

        // Adds the infantry
        for(int i = 1; i <= 500; i++) {
            armyTwo.add(new InfantryUnit("Grunt", 100));
        }

        // Adds the ranged units
        for(int i = 1; i <= 200; i++) {
            armyTwo.add(new RangedUnit("Spearman", 100));
        }

        // Adds the cavalry units
        for(int i = 1; i <= 100; i++) {
            armyTwo.add(new CavalryUnit("Raider", 100));
        }
    }
}
