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

    public Battle() {
        this.armyOne = new Army("Human army");
        this.armyTwo = new Army("Orc Army");
    }

    /**
     * Simulates a battle between two armies.
     * @return the winner of the battle with the remaining units.
     */
    public Army simulate() {
        // Template units for testing.
        addArmy_One();
        addArmy_Two();

        // Army one attacks first.
        int attackTurn = 0;
        Unit attackerUnit = armyOne.getRandom();
        Unit defenderUnit = armyTwo.getRandom();

        while(armyOne.hasUnits() && armyTwo.hasUnits()) {

            // Changes which army attacks each turn.
            if (attackTurn % 2 == 0) {
                attackerUnit = armyOne.getRandom();
                defenderUnit = armyTwo.getRandom();
            } else {
                attackerUnit = armyTwo.getRandom();
                defenderUnit = armyOne.getRandom();
            }

            // Attacker unit attacks defender unit.
            attackerUnit.attack(defenderUnit);

            // If health of defender gets below 0, it dies.
            if (defenderUnit.getHealth() <= 0) {
                if (attackTurn % 2 == 0) {
                    armyTwo.remove(defenderUnit);
                } else {
                    armyOne.remove(defenderUnit);
                }
            }
            attackTurn++;
        }

        // Returns which army still has units.
        if (armyTwo.hasUnits()) {
            return armyTwo;
        } else {
            return armyOne;
        }
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
