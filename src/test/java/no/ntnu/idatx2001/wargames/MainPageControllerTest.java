package no.ntnu.idatx2001.wargames;

import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.UnitFactory;
import no.ntnu.idatx2001.wargames.ui.MainPageController;
import org.junit.Test;
import java.io.File;

import static org.junit.Assert.assertTrue;

public class MainPageControllerTest {

  @Test
  public void testDeleteFilesInFolder() {
    Army army = new Army("Test Army");
    UnitFactory unitFactory = new UnitFactory();

    army.addAll(unitFactory.createUnitBattalion("InfantryUnit", 20, "Footman", 100));

    Army.saveArmyToFile(army.getName(), army, "army-templates/temp/");
    assertTrue(MainPageController.deleteFolder(new File("army-templates/temp/")));
  }
}
