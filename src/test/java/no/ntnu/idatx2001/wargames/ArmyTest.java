package no.ntnu.idatx2001.wargames;

import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.UnitFactory;
import no.ntnu.idatx2001.wargames.model.units.*;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ArmyTest {

  @Test
  public void testRemoveUnitFromArmy() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
    Army army = new Army("Test army");

    army.add(infantryUnit);
    army.remove(infantryUnit);

    assertFalse(army.hasUnits());
  }

  @Test
  public void testAddUnitToArmy() {
    InfantryUnit infantryUnit = new InfantryUnit("Infantry", 100);
    Army army = new Army("Test army");

    army.add(infantryUnit);

    assertTrue(army.hasUnits());
  }

  @Test
  public void testGetInfantryUnits() {
    Unit infantryUnit1 = new InfantryUnit("Infantry1", 100);
    Unit infantryUnit2 = new InfantryUnit("Infantry2", 100);
    Unit cavalryUnit = new CavalryUnit("Cavalry", 100);
    Unit rangedUnit = new RangedUnit("Ranged", 100);
    Unit commanderUnit1 = new CommanderUnit("CommanderUnit1", 180);
    Unit commanderUnit2 = new CommanderUnit("CommanderUnit2", 180);
    Army army = new Army("Test army");

    army.add(infantryUnit1);
    army.add(infantryUnit2);
    army.add(cavalryUnit);
    army.add(rangedUnit);
    army.add(commanderUnit1);
    army.add(commanderUnit2);

    assertEquals(2, army.getInfantryUnits().size());
    assertEquals(1, army.getCavalryUnits().size());
    assertEquals(1, army.getRangedUnits().size());
    assertEquals(2, army.getCommanderUnits().size());
  }

  @Test
  public void testOrcArmyCreationFromFile() {
    Army army = Army.uploadArmyFromFile("army-templates/orc-army.csv");

    assertEquals(50, army.getAmountOfUnits());
    assertTrue(army.hasUnits());
  }

  @Test
  public void testHumanArmyCreationFromFile() {
    Army army = Army.uploadArmyFromFile("army-templates/human-army.csv");

    assertEquals(50, army.getAmountOfUnits());
    assertTrue(army.hasUnits());
  }

  @Test
  public void testUnitFactoryWithSingletonMethod() {
    Army army = new Army("Test army");

    UnitFactory unitFactory = new UnitFactory();

    army.add(unitFactory.createCommanderUnit());
    army.add(unitFactory.createInfantryUnit());
    army.add(unitFactory.createRangedUnit());
    army.add(unitFactory.createCavalryUnit());
    army.add(unitFactory.createArtilleryUnit());

    assertEquals(5, army.getAmountOfUnits());
  }

  @Test
  public void testSaveArmyToFileWithUnitFactory() {
    Army army = new Army("Test army");

//    army.add(new CommanderUnit("Mountain King", 180));
//
//    for (int i = 1; i <= 10; i++) {
//      army.add(new InfantryUnit("Footman", 100));
//    }
//
//    for (int i = 1; i <= 10; i++) {
//      army.add(new RangedUnit("Archer", 100));
//    }
//
//    for (int i = 1; i <= 10; i++) {
//      army.add(new CavalryUnit("Cavalry", 100));
//    }

    Unit commanderUnit = new UnitFactory().createUnit("CommanderUnit", "King", 140);
    army.add(commanderUnit);

    List<Unit> infantryUnits = new UnitFactory().createUnitBattalion("InfantryUnit", 10, "Footman", 100);
    army.addAll(infantryUnits);

    List<Unit> rangedUnits = new UnitFactory().createUnitBattalion("RangedUnit", 10, "Archer", 100);
    army.addAll(rangedUnits);

    List<Unit> cavalryUnits = new UnitFactory().createUnitBattalion("CavalryUnit", 10, "Knight", 100);
    army.addAll(cavalryUnits);

    List<Unit> artilleryUnits = new UnitFactory().createUnitBattalion("ArtilleryUnit", 10, "Scorpion", 40);
    army.addAll(artilleryUnits);

    assertTrue(Army.saveArmyToFile("test", army));
    assertEquals(41, army.getAmountOfUnits());
  }
}
