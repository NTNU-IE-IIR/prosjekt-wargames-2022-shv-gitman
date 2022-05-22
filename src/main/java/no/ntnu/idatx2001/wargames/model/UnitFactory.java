package no.ntnu.idatx2001.wargames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2001.wargames.model.units.*;

public class UnitFactory {

  /**
   * A unit factory that creates a Unit based on unitType, name and health.
   *
   * @param unitType unit type
   * @param unitName unit name
   * @param health   unit health
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

    for (int i = 0; i < units; i++) {
      unitList.add(createUnit(unitType, unitName, health));
    }

    return unitList;
  }

  /**
   * Creates a commander unit with 40 health.
   *
   * @return a Commander unit with 40 health
   */
  public Unit createCommanderUnit() {
    return new CommanderUnit("Commander", 1200);
  }

  /**
   * Creates a Cavalry unit with 20 health.
   *
   * @return a Cavalry unit with 20 health
   */
  public Unit createCavalryUnit() {
    return new CavalryUnit("Cavalry", 100);
  }

  /**
   * Creates an Infantry unit with 20 health.
   *
   * @return an Infantry unit with 20 health
   */
  public Unit createInfantryUnit() {
    return new InfantryUnit("Infantry", 100);
  }

  /**
   * Creates a Ranged unit with 20 health.
   *
   * @return a Ranged unit with 20 health
   */
  public Unit createRangedUnit() {
    return new RangedUnit("Ranged", 100);
  }

  /**
   * Creates a Artillery unit with 20 health.
   *
   * @return a Artillery unit with 20 health
   */
  public Unit createArtilleryUnit() {
    return new ArtilleryUnit("Artillery", 100);
  }
}
