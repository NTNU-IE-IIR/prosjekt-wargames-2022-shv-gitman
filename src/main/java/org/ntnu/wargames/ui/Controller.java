package org.ntnu.wargames.ui;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.ntnu.wargames.*;

/**
 * Responsible for handling the GUI-interaction from the user.
 */
public class Controller {

  private Army armyOne;
  private Army armyTwo;
  private Army placeHolderArmy;
  private String terrain = "";
  private File armyFileOne;
  private File armyFileTwo;
  private ObservableList<Unit> observableList;

  @FXML
  private Text fileOneTextField;
  @FXML
  private Text fileTwoTextField;
  @FXML
  private ListView<String> armyTwoListView;
  @FXML
  private ListView<String> armyOneListView;
  @FXML
  private Button restartSimulationButton;
  @FXML
  private TextArea winnerTextArea;
  @FXML
  private TextField terrainTextField;
  @FXML
  private Button viewArmyOneButton;
  @FXML
  private Button viewArmyTwoButton;

  // For unitView.fxml
  @FXML
  private TableView<Unit> unitTableView;
  @FXML
  private TableColumn<Unit, String> nameTableColumn;
  @FXML
  private TableColumn<Unit, Integer> healthTableColumn;
  @FXML
  private TableColumn<Unit, Integer> armorTableColumn;
  @FXML
  private TableColumn<Unit, Integer> attackTableColumn;

  @FXML
  protected void startSimulation(ActionEvent actionEvent) {
    if (armyOne != null && armyTwo != null && armyOne.hasUnits() && armyTwo.hasUnits() && !(terrain.equals(""))) {
      Battle battle;

      double order = Math.random();

      if (order < 0.5) {
        battle = new Battle(armyOne, armyTwo, terrain);
      } else {
        battle = new Battle(armyTwo, armyOne, terrain);
      }

      Army winner = battle.simulate();
      updateArmyListView(armyOneListView, armyOne);
      updateArmyListView(armyTwoListView, armyTwo);

      winnerTextArea.setText(winner.getName());
      restartSimulationButton.setDisable(false);
    } else {
      winnerTextArea.setText("Make sure both armies have\nunits & you have selected\na battlefield.");
    }
  }

  @FXML
  protected void restartSimulation(ActionEvent actionEvent) {
    restartSimulationButton.setDisable(true);
    winnerTextArea.setText("winner");

    this.armyOne.getAllUnits().clear();
    this.armyTwo.getAllUnits().clear();

    this.armyOne = Army.uploadArmyFromFile(armyFileOne.toString());
    this.armyTwo = Army.uploadArmyFromFile(armyFileTwo.toString());

    updateArmyListView(armyOneListView, armyOne);
    updateArmyListView(armyTwoListView, armyTwo);
  }

  @FXML
  protected void loadArmyOne(ActionEvent actionEvent) {
    loadArmyButton("armyOne", viewArmyOneButton, fileOneTextField);
  }

  @FXML
  protected void loadArmyTwo(ActionEvent actionEvent) {
    loadArmyButton("armyTwo", viewArmyTwoButton, fileTwoTextField);
  }

  @FXML
  protected void armyOneUnitInfoButton(ActionEvent actionEvent) {
    this.placeHolderArmy = armyOne;

    openUnitViewWindow();
  }

  @FXML
  protected void armyTwoUnitInfoButton(ActionEvent actionEvent) {
    this.placeHolderArmy = armyTwo;

    openUnitViewWindow();
  }

  @FXML
  protected void loadUnitInfo(ActionEvent actionEvent) {
    String id = actionEvent.toString();

    setTableColumn(id);
  }

  @FXML
  protected void changeTerrain(ActionEvent actionEvent) {
    String id = actionEvent.toString();

    if (id.contains("FORESTmenuItem")) {
      this.terrain = "FOREST";
      terrainTextField.setText("Terrain: Forest");
    } else if (id.contains("HILLmenuItem")) {
      this.terrain = "HILL";
      terrainTextField.setText("Terrain: Hill");
    } else if (id.contains("PLAINSmenuItem")) {
      this.terrain = "PLAINS";
      terrainTextField.setText("Terrain: Plains");
    }
  }

  /**
   * Saves data between scenes.
   * Used to transfer data to the unitView window.
   *
   * @param armyOne         army one.
   * @param armyTwo         army two
   * @param placeHolderArmy the placeholder army / last selected army
   * @param armyFileOne     army file for army one
   * @param armyFileTwo     army file for army two
   */
  private void initData(
      Army armyOne, Army armyTwo, Army placeHolderArmy, File armyFileOne, File armyFileTwo
  ) {
    this.armyOne = armyOne;
    this.armyTwo = armyTwo;
    this.placeHolderArmy = placeHolderArmy;
    this.armyFileOne = armyFileOne;
    this.armyFileTwo = armyFileTwo;
  }

  /**
   * Loads an army of units from a text file.
   *
   * @param currentArmy Army where units will be added.
   * @param button      button to enable after army is loaded.
   * @param loadText    Text to print which file has been loaded.
   */
  private void loadArmyButton(String currentArmy, Button button, Text loadText) {
    FileChooser fileChooser = new FileChooser();
    Stage fileStage = new Stage();
    File armyFile = fileChooser.showOpenDialog(fileStage);

    if (armyFile != null) {
      if (currentArmy.equals("armyOne")) {
        armyOne = Army.uploadArmyFromFile(armyFile.toString());
        armyFileOne = armyFile;
        loadText.setText("Loaded: " + armyFile.getName() + "\nArmy name: " + armyOne.getName());
        updateArmyListView(armyOneListView, armyOne);
      } else {
        armyTwo = Army.uploadArmyFromFile(armyFile.toString());
        armyFileTwo = armyFile;
        loadText.setText("Loaded: " + armyFile.getName() + "\nArmy name: " + armyTwo.getName());
        updateArmyListView(armyTwoListView, armyTwo);
      }
      button.setDisable(false);
    } else {
      loadText.setText("Loaded: No file found");
    }
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
   * Puts info from units into a table.
   *
   * @param id id of current army.
   */
  private void setTableColumn(String id) {
    nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthTableColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    armorTableColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));
    attackTableColumn.setCellValueFactory(new PropertyValueFactory<>("armor"));

    Army currentArmy = this.placeHolderArmy;

    if (id.contains("menuItemCommander") && !(currentArmy.getCommanderUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(currentArmy, currentArmy.getCommanderUnits().get(0))
      );
    } else if (id.contains("menuItemInfantry") && !(currentArmy.getInfantryUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(currentArmy, currentArmy.getInfantryUnits().get(0))
      );
    } else if (id.contains("menuItemRanged") && !(currentArmy.getRangedUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(currentArmy, currentArmy.getRangedUnits().get(0))
      );
    } else if (id.contains("menuItemCavalry") && !(currentArmy.getCavalryUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(currentArmy, currentArmy.getCavalryUnits().get(0))
      );
    } else if (id.contains("menuItemArtillery") && !(currentArmy.getArtilleryUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(currentArmy, currentArmy.getArtilleryUnits().get(0))
      );
    }
  }

  /**
   * An observable list of units.
   *
   * @param army the army to get units from.
   * @param unit the type of unit.
   * @return an observable list of units of a specific type.
   */
  private ObservableList<Unit> getObservableList(Army army, Unit unit) {
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
   * Opens a new window to view the units in the selected army.
   */
  public void openUnitViewWindow() {
    Parent root;
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("gui/unitView.fxml")
      );
      root = loader.load();

      Controller controller = loader.getController();

      Stage stage = new Stage();
      stage.setTitle("Unit Info");
      stage.setScene(new Scene(
          root, 400, 400)
      );
      controller.initData(armyOne, armyTwo, placeHolderArmy, armyFileOne, armyFileTwo);

      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}