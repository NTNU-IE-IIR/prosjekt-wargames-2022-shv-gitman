package org.ntnu.wargames;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {

  /**
   * Creates a commander unit with 40 health.
   *
   * @return a Commander unit with 40 health
   */
  public Unit createCommanderUnit() {
    return new CommanderUnit("Commander", 40);
  }

  /**
   * Creates a Cavalry unit with 20 health.
   *
   * @return a Cavalry unit with 20 health
   */
  public Unit createCavalryUnit() {
    return new CavalryUnit("Cavalry", 20);
  }

  /**
   * Creates an Infantry unit with 20 health.
   *
   * @return an Infantry unit with 20 health
   */
  public Unit createInfantryUnit() {
    return new InfantryUnit("Infantry", 20);
  }

  /**
   * Creates a Ranged unit with 20 health.
   *
   * @return a Ranged unit with 20 health
   */
  public Unit createRangedUnit() {
    return new RangedUnit("Ranged", 20);
  }

  /**
   * Creates a Artillery unit with 20 health.
   *
   * @return a Artillery unit with 20 health
   */
  public Unit createArtilleryUnit() {
    return new ArtilleryUnit("Artillery", 20);
  }

  /**
   * A unit factory that creates a Unit based on unitType, name and health.
   *
   * @param unitType unit type
   * @param unitName unit name
   * @param health unit health
   * @return a Unit with a given name & health, or null if invalid unitType is given.
   */
  public Unit createUnit(String unitType, String unitName, int health) {
    return switch (unitType) {
      case "CommanderUnit" -> new CommanderUnit(unitName, health);
      case "CavalryUnit" -> new CavalryUnit(unitName, health);
      case "InfantryUnit" -> new InfantryUnit(unitName, health);
      case "RangedUnit" -> new RangedUnit(unitName, health);
      case "ArtilleryUnit" -> new ArtilleryUnit(unitName, health);
      default -> null;
    };
  }

  /**
   * Creates a battalion of a single unit type.
   *
   * @param unitType Unit type of battalion
   * @param units    amount of units in the battalion
   * @param unitName name of units
   * @param health   health of units
   * @return a list of Unit
   */
  public List<Unit> createUnitBattalion(String unitType, int units, String unitName, int health) {
    List<Unit> unitList = new ArrayList<>();

    switch (unitType) {
      case "CommanderUnit":
        for (int i = 0; i < units; i++) {
          unitList.add(new CommanderUnit(unitName, health));
        }
        break;
      case "CavalryUnit":
        for (int i = 0; i < units; i++) {
          unitList.add(new CavalryUnit(unitName, health));
        }
        break;
      case "InfantryUnit":
        for (int i = 0; i < units; i++) {
          unitList.add(new InfantryUnit(unitName, health));
        }
        break;
      case "RangedUnit":
        for (int i = 0; i < units; i++) {
          unitList.add(new RangedUnit(unitName, health));
        }
        break;
      case "ArtilleryUnit":
        for (int i = 0; i < units; i++) {
          unitList.add(new ArtilleryUnit(unitName, health));
        }
        break;
      default:
        return unitList;
    }
    return unitList;
  }
}
