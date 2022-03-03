package org.ntnu.wargames;

public class BattleSimulatorApp {
    private final Battle battle;

    /**
     * Creates a battle with predefined names and units.
     */
    public BattleSimulatorApp() {
        this.battle = new Battle();
    }

    /**
     * Runs the simulation and prints out the winner
     * with remaining units.
     */
    public void runBattleSimulation() {
        Army winner = battle.simulate();

        System.out.println("Winner: " + winner.toString());
        System.out.println("Remaining units: ");
        winner.printAllUnits();
    }

    /**
     * Start of the battle simulation app.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        BattleSimulatorApp battleSimulator = new BattleSimulatorApp();
        battleSimulator.runBattleSimulation();
    }
}
