package no.ntnu.idatx2001.wargames.model;

import no.ntnu.idatx2001.wargames.model.units.*;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class ArmyTest {

  private UnitFactory unitFactory = new UnitFactory();

  @Test
  public void testArmyCreation() {
    Army army = new Army("Test army");

    assertEquals("Test army", army.getName());
  }

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
  public void testAddAllUnitsToArmy() {
    Army testArmy = new Army("Test Army");
    List<Unit> testUnits = unitFactory.createUnitBattalion("InfantryUnit", 100, "Footman", 100);

    testArmy.addAll(testUnits);
    assertEquals(testUnits.size(), testArmy.getAllUnits().size());
  }

  @Test
  public void testGetRandom() {
    Army testArmy = new Army("Test Army");
    testArmy.add(unitFactory.createCavalryUnit());
    testArmy.add(unitFactory.createInfantryUnit());
    assertNotNull(testArmy.getRandom());
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
    Army army = Army.uploadArmyFromFile("army-templates/Orc Army.csv");

    assertEquals(50, army.getAmountOfUnits());
  }

  @Test
  public void testHumanArmyCreationFromFile() {
    Army army = Army.uploadArmyFromFile("army-templates/Human army.csv");

    assertEquals(50, army.getAmountOfUnits());
  }

  @Test
  public void testUnitFactoryWithSingletonMethod() {
    Army army = new Army("Test army");

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

    assertTrue(Army.saveArmyToFile("Test army", army, "army-templates/"));
    assertEquals(41, army.getAmountOfUnits());
  }

  @Test
  public void testArmyHashCode() {
    Army army1 = new Army("army1");
    Army army2 = new Army("army2");
    Army army3 = army1;

    assertEquals(army1.hashCode(), army3.hashCode());
    assertNotEquals(army2.hashCode(), army3.hashCode());
  }

  @Test
  public void testArmyEquals() {
    Army army1 = new Army("army1");
    Army army2 = new Army("army2");
    Army army3 = army1;

    assertTrue(army3.equals(army1));
    assertTrue(army1.equals(army3));
    assertFalse(army2.equals(army1));
  }
}
