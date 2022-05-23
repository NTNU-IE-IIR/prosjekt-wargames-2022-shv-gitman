package no.ntnu.idatx2001.wargames.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.Battle;
import no.ntnu.idatx2001.wargames.model.UnitFactory;
import no.ntnu.idatx2001.wargames.model.units.*;
import no.ntnu.idatx2001.wargames.ui.dialog.AddUnitDialog;
import no.ntnu.idatx2001.wargames.ui.dialog.ViewArmyDialog;

/**
 * Responsible for handling the GUI-interaction from the user.
 */
public class MainPageController {

  private Army armyOne = new Army("Army 1");
  private Army armyTwo = new Army("Army 2");

  // Placeholder files.
  private File placeHolderArmyOneFile;
  private File placeHolderArmyTwoFile;

  private static final String FOREST = "FOREST";
  private static final String HILL = "HILL";
  private static final String PLAINS = "PLAINS";

  // Sets default terrain to forest.
  private String terrain = FOREST;

  private String armyTemplateDir = "army-templates/";

  @FXML
  private Text fileOneText;
  @FXML
  private Text fileTwoText;
  @FXML
  private ListView<String> armyTwoListView;
  @FXML
  private ListView<String> armyOneListView;
  @FXML
  private Button restartSimulationButton;
  @FXML
  private TextArea battleFieldTextArea;
  @FXML
  private Text terrainTextField;
  @FXML
  private Button viewArmyOneButton;
  @FXML
  private Button viewArmyTwoButton;
  @FXML
  private Button saveArmyOneButton;
  @FXML
  private Button saveArmyTwoButton;
  @FXML
  private TextField armyOneNameTextField;
  @FXML
  private TextField armyTwoNameTextField;
  @FXML
  private Button clearArmyOneButton;
  @FXML
  private Button clearArmyTwoButton;
  @FXML
  private Text textCommanderMod;
  @FXML
  private Text textInfantryMod;
  @FXML
  private Text textRangedMod;
  @FXML
  private Text textCavalryMod;
  @FXML
  private Text textArtilleryMod;

  /**
   * Adds a listener to the army-name TextFields at application initialization to
   * automatically update the name of each Army.
   */
  @FXML
  public void initialize() {
    armyOneNameTextField.textProperty().addListener((observable, oldValue, newValue)
        -> armyOne.setArmyName(newValue));
    armyTwoNameTextField.textProperty().addListener((observable, oldValue, newValue)
        -> armyTwo.setArmyName(newValue));
    updateBonusTable(FOREST);
  }



  /**
   * Starts a battle between armyOne and armyTwo and presents the
   * user with the result of the battle.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void startSimulation(ActionEvent actionEvent) {
    if (armyOne != null && armyTwo != null && armyOne.hasUnits()
        && armyTwo.hasUnits() && !(terrain.equals(""))) {
      Battle battle;

      // Determines who attacks first
      double order = Math.random();
      if (order < 0.5) {
        battle = new Battle(armyOne, armyTwo, terrain);
      } else {
        battle = new Battle(armyTwo, armyOne, terrain);
      }

      placeHolderArmyOneFile = saveTempFile(armyOne);
      placeHolderArmyTwoFile = saveTempFile(armyTwo);

      Army winner = battle.simulate();
      updateArmyListView(armyOneListView, armyOne);
      updateArmyListView(armyTwoListView, armyTwo);

      battleFieldTextArea.setText(winner.getName());
      restartSimulationButton.setDisable(false);
    } else {
      battleFieldTextArea.setText(
          "Make sure both armies have units\n& you have selected a battlefield."
      );
    }
  }

  /**
   * Restarts the battle, uses the backup .csv files from the
   * "army-templates/temp" folder to restore the armies.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void restartSimulation(ActionEvent actionEvent) {
    restartSimulationButton.setDisable(true);
    battleFieldTextArea.setText("winner");

    this.armyOne.getAllUnits().clear();
    this.armyTwo.getAllUnits().clear();
    this.armyOne = Army.uploadArmyFromFile(placeHolderArmyOneFile.toString());
    this.armyTwo = Army.uploadArmyFromFile(placeHolderArmyTwoFile.toString());

    updateArmyListView(armyOneListView, armyOne);
    updateArmyListView(armyTwoListView, armyTwo);
  }

  /**
   * Loads an army from a .csv file.
   * Creates an extra .csv file to store the army for later use.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void loadArmyOne(ActionEvent actionEvent) {
    loadArmyButton("armyOne", viewArmyOneButton,
        saveArmyOneButton, clearArmyOneButton, fileOneText, armyOneNameTextField
    );
    placeHolderArmyOneFile = saveTempFile(armyOne);
  }

  /**
   * Loads an army from a .csv file.
   * Creates an extra .csv file to store the army for later use.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void loadArmyTwo(ActionEvent actionEvent) {
    loadArmyButton("armyTwo", viewArmyTwoButton,
        saveArmyTwoButton, clearArmyTwoButton, fileTwoText, armyTwoNameTextField
    );
    placeHolderArmyTwoFile = saveTempFile(armyTwo);
  }

  /**
   * Responsible for opening and communicating with the AddUnit Dialog.
   * Will return a list of units to add to armyOne.
   * If armyOne has units, it will create an extra .csv file to store the army.
   *
   * @param actionEvent event handler
   */
  @FXML
  public void addArmyOneUnit(ActionEvent actionEvent) {
    this.armyOne = addUnits(
        armyOne, armyOneListView, viewArmyOneButton,
        saveArmyOneButton, clearArmyOneButton, armyOneNameTextField, 1
    );
    if (this.armyOne.hasUnits()) {
      placeHolderArmyOneFile = saveTempFile(armyOne);
    }
  }

  /**
   * Responsible for opening and communicating with the AddUnit Dialog.
   * Will return a list of units to add to armyTwo.
   * If armyTwo has units, it will create an extra .csv file to store the army.
   *
   * @param actionEvent event handler
   */
  @FXML
  public void addArmyTwoUnit(ActionEvent actionEvent) {
    this.armyTwo = addUnits(
        armyTwo, armyTwoListView, viewArmyTwoButton,
        saveArmyTwoButton, clearArmyTwoButton, armyTwoNameTextField, 2
    );
    if (this.armyTwo.hasUnits()) {
      placeHolderArmyTwoFile = saveTempFile(armyTwo);
    }
  }

  /**
   * Responsible for opening the ViewArmy Dialog.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void armyOneUnitInfoButton(ActionEvent actionEvent) {
    ViewArmyDialog viewArmyDialog = new ViewArmyDialog(armyOne);
    viewArmyDialog.showAndWait();
  }

  /**
   * Responsible for opening the ViewArmy Dialog.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void armyTwoUnitInfoButton(ActionEvent actionEvent) {
    ViewArmyDialog viewArmyDialog = new ViewArmyDialog(armyTwo);
    viewArmyDialog.showAndWait();
  }

  /**
   * Saves army one to a file of the Army name to the
   * "army-templates/" folder in the directory.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void saveArmyOneToFile(ActionEvent actionEvent) {
    if (saveConfirmationAlert(armyOne)) {
      checkArmyTemplateDir();
      if (Army.saveArmyToFile(armyOne.getName(), armyOne, armyTemplateDir)) {
        saveSuccessAlert(armyOne);
      } else {
        saveErrorAlert();
      }
    }
  }

  /**
   * Saves army two to a file of the Army name to the
   * "army-templates/" folder in the directory.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void saveArmyTwoToFile(ActionEvent actionEvent) {
    if (saveConfirmationAlert(armyTwo)) {
      checkArmyTemplateDir();
      if (Army.saveArmyToFile(armyTwo.getName(), armyTwo, armyTemplateDir)) {
        saveSuccessAlert(armyTwo);
      } else {
        saveErrorAlert();
      }
    }
  }

  /**
   * Clears army one of all units.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void clearArmyOneButton(ActionEvent actionEvent) {
    armyOne.getAllUnits().clear();
    placeHolderArmyOneFile = saveTempFile(armyOne);
    updateArmyListView(armyOneListView, armyOne);
  }

  /**
   * Clears army two of all units.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void clearArmyTwoButton(ActionEvent actionEvent) {
    armyTwo.getAllUnits().clear();
    placeHolderArmyTwoFile = saveTempFile(armyTwo);
    updateArmyListView(armyTwoListView, armyTwo);
  }

  /**
   * Changes terrain of the battle from selected menu-item.
   *
   * @param actionEvent event handler
   */
  @FXML
  protected void changeTerrain(ActionEvent actionEvent) {
    String id = actionEvent.toString();

    if (id.contains("FORESTmenuItem")) {
      this.terrain = FOREST;
      terrainTextField.setText(FOREST);
      updateBonusTable(FOREST);
    } else if (id.contains("HILLmenuItem")) {
      this.terrain = HILL;
      terrainTextField.setText(HILL);
      updateBonusTable(HILL);
    } else if (id.contains("PLAINSmenuItem")) {
      this.terrain = PLAINS;
      terrainTextField.setText(PLAINS);
      updateBonusTable(PLAINS);
    }
  }

  /**
   * Loads an army of units from a text file.
   *
   * @param currentArmy     Army where units will be added.
   * @param viewUnitButton  view-unit-button to enable after army is loaded.
   * @param saveButton      save-army-button to enable after army is loaded.
   * @param clearArmyButton clear-army to enable after army is loaded.
   * @param fileText        Text to print which file has been loaded.
   * @param armyNameText    Text to print which army has been loaded.
   */
  private void loadArmyButton(String currentArmy, Button viewUnitButton, Button saveButton,
                              Button clearArmyButton, Text fileText, TextField armyNameText) {
    FileChooser fileChooser = new FileChooser();
    Stage fileStage = new Stage();
    File armyFile = fileChooser.showOpenDialog(fileStage);

    if (armyFile != null) {
      if (currentArmy.equals("armyOne")) {
        armyOne = Army.uploadArmyFromFile(armyFile.toString());
        placeHolderArmyOneFile = armyFile;

        fileText.setText("Loaded: " + armyFile.getName());
        armyNameText.setText(armyOne.getName());

        updateArmyListView(armyOneListView, armyOne);
      } else {
        armyTwo = Army.uploadArmyFromFile(armyFile.toString());
        placeHolderArmyTwoFile = armyFile;

        fileText.setText("Loaded: " + armyFile.getName());
        armyNameText.setText(armyTwo.getName());

        updateArmyListView(armyTwoListView, armyTwo);
      }
      viewUnitButton.setDisable(false);
      saveButton.setDisable(false);
      clearArmyButton.setDisable(false);
    } else {
      fileText.setText("Loaded: No file found");
    }
  }

  /**
   * Presents user with a Dialog Pane to add units to an army.
   *
   * @param army            army to add units to
   * @param listView        list view to present units with
   * @param viewArmyButton  button to view info about units in army
   * @param saveArmyButton  button to save army
   * @param clearArmyButton button to clear army
   * @param armyNameText    text-field to display army name
   * @param armyNumber      army number
   */
  private Army addUnits(
      Army army, ListView<String> listView, Button viewArmyButton, Button saveArmyButton,
      Button clearArmyButton, TextField armyNameText, int armyNumber
  ) {
    AddUnitDialog addUnitDialog = new AddUnitDialog();
    Optional<List<Unit>> result = addUnitDialog.showAndWait();

    if (army == null) {
      army = new Army("Army " + armyNumber);
    }

    if (result.isPresent()) {
      List<Unit> units = result.get();
      if (!units.isEmpty()) {
        army.addAll(units);
        updateArmyListView(listView, army);
        armyNameText.setText(army.getName());
        viewArmyButton.setDisable(false);
        saveArmyButton.setDisable(false);
        clearArmyButton.setDisable(false);
      }
    }

    return army;
  }

  /**
   * Method to update list view with unit information about an army.
   *
   * @param listView the list to put info into
   * @param army     army to be put in list
   */
  private void updateArmyListView(ListView<String> listView, Army army) {
    listView.getItems().clear();

    // Displays amount of units in List View.
    listView.getItems().add("Units: " + army.getAmountOfUnits());
    listView.getItems().add("Commander units: " + army.getCommanderUnits().size());
    listView.getItems().add("Infantry units: " + army.getInfantryUnits().size());
    listView.getItems().add("Ranged units: " + army.getRangedUnits().size());
    listView.getItems().add("Cavalry units: " + army.getCavalryUnits().size());
    listView.getItems().add("Artillery units: " + army.getArtilleryUnits().size());
  }

  /**
   * An observable list of units.
   *
   * @param army the army to get units from.
   * @param unit the type of unit.
   * @return an observable list of units of a specific type.
   */
  public static ObservableList<Unit> getUnits(Army army, Unit unit) {
    ObservableList<Unit> units = FXCollections.observableArrayList();

    if (unit instanceof CommanderUnit) {
      units.addAll(army.getCommanderUnits());
    }
    if (unit instanceof InfantryUnit) {
      units.addAll(army.getInfantryUnits());
    }
    if (unit instanceof RangedUnit) {
      units.addAll(army.getRangedUnits());
    }
    if (unit instanceof CavalryUnit && !(unit instanceof CommanderUnit)) {
      units.addAll(army.getCavalryUnits());
    }
    if (unit instanceof ArtilleryUnit) {
      units.addAll(army.getArtilleryUnits());
    }
    return units;
  }

  /**
   * Presents the user with a confirmation dialog to save or not save the army.
   *
   * @param army army to save
   * @return save confirmation, true if yes-button is pressed, false if not
   */
  private boolean saveConfirmationAlert(Army army) {
    boolean saveConfirmation = false;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Save confirmation");
    alert.setHeaderText("Save confirmation");
    alert.setContentText("Are you sure you want to save " + army.getName()
        + "? This will overwrite saved armies with the same name");

    Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
    yesButton.setText("Yes");
    noButton.setText("No");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      saveConfirmation = (result.get() == ButtonType.OK);
    }

    return saveConfirmation;
  }

  /**
   * Presents an alert to the user when an army is successfully saved.
   *
   * @param army army that has been saved
   */
  private void saveSuccessAlert(Army army) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Save army: " + army.getName());
    alert.setHeaderText("Save Successful!");
    alert.setContentText(armyOne.getName() + " was successfully saved as \""
        + armyOne.getName() + ".csv\" to the army-template directory");
    alert.showAndWait();
  }

  /**
   * Alert the user if there is an error in saving the army.
   */
  private void saveErrorAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("ERROR");
    alert.setHeaderText("Save Error!");
    alert.setContentText("There was an error when saving the army.");
    alert.showAndWait();
  }

  /**
   * Creates a file containing an army in the temp/ folder.
   *
   * @param army Army to save
   * @return a new file if successfully saved, null if not
   */
  private File saveTempFile(Army army) {
    File placeHolderFile = null;
    String tempFolder = "army-templates/temp/";

    checkArmyTemplateDir();

    // creates temp directory if it doesn't exist.
    File tempDirectory = new File(tempFolder);
    if (!tempDirectory.exists()) {
      tempDirectory.mkdir();
    }

    if (Army.saveArmyToFile(army.getName() + "-temp", army, tempFolder)) {
      placeHolderFile = new File(tempFolder + army.getName() + "-temp.csv");
    }

    return placeHolderFile;
  }

  /**
   * Creates army-templates directory if it doesn't exist.
   */
  private void checkArmyTemplateDir() {
    File armyTemplateDirectory = new File(armyTemplateDir);
    if (!armyTemplateDirectory.exists()) {
      armyTemplateDirectory.mkdir();
    }
  }

  /**
   * Deletes all files in a folder.
   *
   * @param folder folder to delete files in
   * @return true if file files are deleted, false if not
   */
  public static boolean deleteFolderContent(File folder) {
    boolean deleteSuccess = false;

    File[] files = folder.listFiles();
    if (files != null) {
      for (File f : files) {
        f.delete();
      }
      deleteSuccess = true;
    }

    return deleteSuccess;
  }

  /**
   * Updates the modifier table with values for the different units
   * depending on chosen terrain.
   *
   * @param terrain chosesn terrain to display modifier of
   */
  private void updateBonusTable(String terrain) {
    UnitFactory unitFactory = new UnitFactory();

    CommanderUnit dummyCommander = (CommanderUnit) unitFactory.createCommanderUnit();
    InfantryUnit dummyInfantry = (InfantryUnit) unitFactory.createInfantryUnit();
    RangedUnit dummyRanged  = (RangedUnit) unitFactory.createRangedUnit();
    CavalryUnit dummyCavalry  = (CavalryUnit) unitFactory.createCavalryUnit();
    ArtilleryUnit dummyArtillery  = (ArtilleryUnit) unitFactory.createArtilleryUnit();

    List<Unit> units = new ArrayList<>();
    units.add(dummyCommander);
    units.add(dummyInfantry);
    units.add(dummyRanged);
    units.add(dummyCavalry);
    units.add(dummyArtillery);

    List<Text> bonusTexts = new ArrayList<>();
    bonusTexts.add(textCommanderMod);
    bonusTexts.add(textInfantryMod);
    bonusTexts.add(textRangedMod);
    bonusTexts.add(textCavalryMod);
    bonusTexts.add(textArtilleryMod);

    for (int i = 0; i < bonusTexts.size(); i++) {
      Text currentText = bonusTexts.get(i);
      Unit currentUnit = units.get(i);

      switch (terrain) {
        case FOREST -> currentText.setText("" + currentUnit.getForestModifier());
        case HILL -> currentText.setText("" + currentUnit.getHillModifier());
        case PLAINS -> currentText.setText("" + currentUnit.getPlainsModifier());
        default -> currentText.setText("0");
      }
    }
  }
}