package org.ntnu.wargames;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents an army with a list of units.
 */
public class Army {
  private final String name;
  private List<Unit> units = new ArrayList<>();
  private final Random random = new Random();

  /**
   * Creates an empty army.
   *
   * @param name the name of the army.
   */
  Army(String name) {
    this.name = name;
  }

  /**
   * Creates an army with an existing list of units.
   *
   * @param name  the name of the army.
   * @param units the list of units.
   */
  Army(String name, List<Unit> units) {
    this.name = name;
    this.units = units;
  }

  /**
   * Adds an unit to the army.
   *
   * @param unit the unit to be added.
   */
  public void add(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit is empty");
    } else {
      this.units.add(unit);
    }
  }

  /**
   * Adds a list of units to the army.
   *
   * @param units the list of unit to be added.
   */
  public void addAll(List<Unit> units) {
    this.units.addAll(units);
  }

  /**
   * Removes a unit from the army.
   *
   * @param unit the unit to be removed.
   */
  public void remove(Unit unit) {
    this.units.remove(unit);
  }

  /**
   * Checks if the army has units.
   * Returns true if the army has units, false if not.
   *
   * @return true if the army has units, false if not.
   */
  public boolean hasUnits() {
    return !units.isEmpty();
  }

  /**
   * Returns a list of all units in the army.
   *
   * @return a list of all units in the army.
   */
  public List<Unit> getAllUnits() {
    return this.units;
  }

  /**
   * Prints all unit in the Army with the amount
   * of attack- and defence turns.
   */
  public void printAllUnits() {
    units.forEach(unit -> System.out.println("  " + unit.getName()
        + " - " + unit.getHealth()
        + " hp. Attacked: " + unit.getUnitAttackTurn()
        + " times, defended " + unit.getUnitDefenceTurn() + " times."));
  }

  /**
   * Returns a random unit from the army.
   *
   * @return a random unit from the army.
   */
  public Unit getRandom() {
    return units.get(random.nextInt(units.size()));
  }

  /**
   * Gets amount of units in the army.
   *
   * @return amount of units in the army.
   */
  public int getAmountOfUnits() {
    int size = 0;
    for (int i = 0; i < units.size(); i++) {
      size++;
    }
    return size;
  }

  /**
   * Returns a list of infantry units in the army.
   *
   * @return a list of infantry units in the army.
   */
  public List<Unit> getInfantryUnits() {
    return units.stream()
        .filter(InfantryUnit.class::isInstance)
        .toList();

  }

  /**
   * Returns a list of cavalry units in the army.
   *
   * @return a list of cavalry units in the army.
   */
  public List<Unit> getCavalryUnits() {
    return units.stream()
        .filter(CavalryUnit.class::isInstance)
        .filter(unit -> !(unit instanceof CommanderUnit))
        .toList();
  }

  /**
   * Returns a list of ranged units in the army.
   *
   * @return a list of ranged units in the army.
   */
  public List<Unit> getRangedUnits() {
    return units.stream()
        .filter(RangedUnit.class::isInstance)
        .toList();
  }

  /**
   * Returns a list of commander units in the army.
   *
   * @return a list of commander units in the army.
   */
  public List<Unit> getCommanderUnits() {
    return units.stream()
        .filter(CommanderUnit.class::isInstance)
        .toList();
  }

  /**
   * Returns a list of artillery units in the army.
   *
   * @return a list of artillery units in the army.
   */
  public List<Unit> getArtilleryUnits() {
    return units.stream()
        .filter(ArtilleryUnit.class::isInstance)
        .toList();
  }

  /**
   * Creates an army from a text file.
   *
   * @param filename name of file.
   * @return an army.
   */
  public static Army uploadArmyFromFile(String filename) {
    List<Unit> units = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

      String line;
      String name = "";
      int currentLineNumber = 0;

      if ((line = br.readLine()) != null) {
        name = line;

        while ((line = br.readLine()) != null) {
          String[] currentLine = line.split(",");

          if (!(currentLine[2].equals("null"))) {
            switch (currentLine[0]) {
              case "InfantryUnit" -> {
                Unit newUnit = new InfantryUnit(currentLine[1], Integer.parseInt(currentLine[2]));
                units.add(newUnit);
              }
              case "RangedUnit" -> {
                Unit newUnit = new RangedUnit(currentLine[1], Integer.parseInt(currentLine[2]));
                units.add(newUnit);
              }
              case "CavalryUnit" -> {
                Unit newUnit = new CavalryUnit(currentLine[1], Integer.parseInt(currentLine[2]));
                units.add(newUnit);
              }
              case "CommanderUnit" -> {
                Unit newUnit = new CommanderUnit(currentLine[1], Integer.parseInt(currentLine[2]));
                units.add(newUnit);
              }
              case "ArtilleryUnit" -> {
                Unit newUnit = new ArtilleryUnit(currentLine[1], Integer.parseInt(currentLine[2]));
                units.add(newUnit);
              }
            }
          } else {
            System.out.println("Health was null at line: " + currentLineNumber + " in " + filename);
          }
          currentLineNumber++;
        }
      }

      return new Army(name, units);

    } catch (IOException ioe) {
      System.out.println("Something went wrong..\nDetails: " + ioe.getMessage());
      return new Army("Army not found", units);
    }
  }

  /**
   * Saves an army from to cvs. file.
   *
   * @param filename name of file
   * @return saveStatus, true if successfully saved army to a file, false if not.
   */
  public boolean saveArmyToFile(String filename) {
    boolean saveStatus = false;
    try {
      FileWriter file = new FileWriter(filename);

      file.write(this.name + "\n");

      for (Unit unit : this.units) {
        file.write(unit.getClass().getSimpleName() + ","
            + unit.getName() + ","
            + unit.getHealth() + "\n");
      }
      file.close();
      saveStatus = true;
    } catch (IOException ioe) {
      System.out.println("Something went wrong..\nDetails: " + ioe.getMessage());
    }
    return saveStatus;
  }

  /**
   * Returns name of Army.
   *
   * @return name of Army
   */
  public String getName() {
    return name;
  }

  /**
   * Returns a string representing the whole army.
   *
   * @return a string representing the whole army.
   */
  @Override
  public String toString() {
    return "name='" + name + '\''
        + ", units=" + getAmountOfUnits();
  }

  /**
   * Checks if the army is the same as another army.
   * Both name and units must be the same to return true.
   *
   * @param o the object to be checked if it is the same as this army.
   * @return true if the object is the same as this army, false if not.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Army army = (Army) o;
    return Objects.equals(name, army.name) && Objects.equals(units, army.units);
  }

  /**
   * Returns a hash value of the Army object.
   *
   * @return a hash value of the Army object.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, units);
  }
}
