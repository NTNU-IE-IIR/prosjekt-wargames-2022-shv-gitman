package no.ntnu.idatx2001.wargames.ui;

import java.io.File;
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
import no.ntnu.idatx2001.wargames.ui.dialog.AddUnitDialog;
import no.ntnu.idatx2001.wargames.ui.dialog.ViewArmyDialog;
import no.ntnu.idatx2001.wargames.model.units.*;

/**
 * Responsible for handling the GUI-interaction from the user.
 */
public class MainPageController {

  private Army armyOne = new Army("Army 1");
  private Army armyTwo = new Army("Army 2");
  private Army placeHolderArmyOne = new Army("");
  private Army placeHolderArmyTwo = new Army("");
  private File placeHolderArmyOneFile;
  private File placeHolderArmyTwoFile;
  // Sets default terrain to forest
  private String terrain = "FOREST";

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
  public void initialize() {
    armyOneNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      armyOne.setArmyName(newValue);
    });
    armyTwoNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
      armyTwo.setArmyName(newValue);
    });
  }

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

      Army winner = battle.simulate();
      updateArmyListView(armyOneListView, armyOne);
      updateArmyListView(armyTwoListView, armyTwo);

      battleFieldTextArea.setText(winner.getName());
      restartSimulationButton.setDisable(false);
    } else {
      battleFieldTextArea.setText(
          "Make sure both armies have\nunits & you have selected\na battlefield."
      );
    }
  }

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

  @FXML
  protected void loadArmyOne(ActionEvent actionEvent) {
    loadArmyButton("armyOne", viewArmyOneButton,
        saveArmyOneButton, fileOneText, armyOneNameTextField
    );
    if (Army.saveArmyToFile(armyOne.getName() + "-temp", armyOne, "army-templates/temp/")) {
      placeHolderArmyOneFile = new File("army-templates/temp/" + armyOne.getName() + "-temp.csv");
    }
  }

  @FXML
  protected void loadArmyTwo(ActionEvent actionEvent) {
    loadArmyButton("armyTwo", viewArmyTwoButton,
        saveArmyTwoButton, fileTwoText, armyTwoNameTextField
    );
    if (Army.saveArmyToFile(armyTwo.getName() + "-temp", armyTwo, "army-templates/temp/")) {
      placeHolderArmyTwoFile = new File("army-templates/temp/" + armyTwo.getName() + "-temp.csv");
    }
  }

  @FXML
  public void addArmyOneUnit(ActionEvent actionEvent) {
    this.armyOne = addUnits(
        armyOne, armyOneListView, viewArmyOneButton,
        saveArmyOneButton, armyOneNameTextField, 1
    );
    if (Army.saveArmyToFile(armyOne.getName() + "-temp", armyOne, "army-templates/temp/")) {
      placeHolderArmyOneFile = new File("army-templates/temp/" + armyOne.getName() + "-temp.csv");
    }
  }

  @FXML
  public void addArmyTwoUnit(ActionEvent actionEvent) {
    this.armyTwo = addUnits(
        armyTwo, armyTwoListView, viewArmyTwoButton,
        saveArmyTwoButton, armyTwoNameTextField, 2
    );
    if (Army.saveArmyToFile(armyTwo.getName() + "-temp", armyTwo, "army-templates/temp/")) {
      placeHolderArmyTwoFile = new File("army-templates/temp/" + armyTwo.getName() + "-temp.csv");
    }
  }

  @FXML
  protected void armyOneUnitInfoButton(ActionEvent actionEvent) {
    System.out.println(armyOne.getAmountOfUnits());
    ViewArmyDialog viewArmyDialog = new ViewArmyDialog(armyOne);
    viewArmyDialog.showAndWait();
  }

  @FXML
  protected void armyTwoUnitInfoButton(ActionEvent actionEvent) {
    System.out.println(armyTwo.getAmountOfUnits());
    ViewArmyDialog viewArmyDialog = new ViewArmyDialog(armyTwo);
    viewArmyDialog.showAndWait();
  }

  @FXML
  protected void saveArmyOneToFile(ActionEvent actionEvent) {
    if (saveConfirmationAlert(armyOne)) {
      if (Army.saveArmyToFile(armyOne.getName(), armyOne, "army-templates/")) {
        saveSuccessAlert(armyOne);
      } else {
        saveErrorAlert();
      }
    }
  }

  @FXML
  protected void saveArmyTwoToFile(ActionEvent actionEvent) {
    if (saveConfirmationAlert(armyTwo)) {
      if (Army.saveArmyToFile(armyTwo.getName(), armyTwo, "army-templates/")) {
        saveSuccessAlert(armyTwo);
      } else {
        saveErrorAlert();
      }
    }
  }

  @FXML
  protected void changeTerrain(ActionEvent actionEvent) {
    String id = actionEvent.toString();

    if (id.contains("FORESTmenuItem")) {
      this.terrain = "FOREST";
      terrainTextField.setText("Terrain: FOREST");
    } else if (id.contains("HILLmenuItem")) {
      this.terrain = "HILL";
      terrainTextField.setText("Terrain: HILL");
    } else if (id.contains("PLAINSmenuItem")) {
      this.terrain = "PLAINS";
      terrainTextField.setText("Terrain: PLAINS");
    }
  }

  /**
   * Loads an army of units from a text file.
   * TODO: Duplicate code
   *
   * @param currentArmy    Army where units will be added.
   * @param viewUnitButton view-unit-button to enable after army is loaded.
   * @param saveButton     save-army-button to enable after army is loaded.
   * @param fileText       Text to print which file has been loaded.
   * @param armyNameText   Text to print which army has been loaded.
   */
  private void loadArmyButton(String currentArmy, Button viewUnitButton, Button saveButton,
                              Text fileText, TextField armyNameText) {
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
    } else {
      fileText.setText("Loaded: No file found");
    }
  }

  /**
   * Presents user with a Dialog Pane to add units to an army.
   *
   * @param army           army to add units to
   * @param listView       list view to present units with
   * @param viewArmyButton button to view info about units in army
   * @param saveArmyButton button to save army
   * @param armyNumber     army number
   */
  private Army addUnits(
      Army army, ListView<String> listView, Button viewArmyButton, Button saveArmyButton, TextField armyNameText, int armyNumber
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
   * Deletes all files in a folder.
   *
   * @param folder folder to delete
   */
  public static boolean deleteFolder(File folder) {
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
}