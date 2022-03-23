package org.ntnu.wargames;

import java.io.File;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Responsible for handling the GUI-interaction from the user.
 */
public class Controller {

  private Army armyOne;
  private Army armyTwo;
  private Army placeholderArmyOne;
  private Army placeholderArmyTwo;

  @FXML
  private Text fileOneTextField;
  @FXML
  private Text fileTwoTextField;
  @FXML
  private MenuButton armyOneMenuButton;
  @FXML
  private MenuButton armyTwoMenuButton;
  @FXML
  private TableColumn armyOneTableColumnName;
  @FXML
  private TableColumn armyOneTableColumnHealth;
  @FXML
  private TableColumn armyOneTableColumnAttack;
  @FXML
  private TableColumn armyOneTableColumnDefence;
  @FXML
  private TableColumn armyTwoTableColumnName;
  @FXML
  private TableColumn armyTwoTableColumnHealth;
  @FXML
  private TableColumn armyTwoTableColumnAttack;
  @FXML
  private TableColumn armyTwoTableColumnDefence;
  @FXML
  private MenuItem armyOneMenuItemCommander;
  @FXML
  private MenuItem armyOneMenuItemInfantry;
  @FXML
  private MenuItem armyOneMenuItemRanged;
  @FXML
  private MenuItem armyOneMenuItemCavalry;
  @FXML
  private MenuItem armyTwoMenuItemCommander;
  @FXML
  private MenuItem armyTwoMenuItemInfantry;
  @FXML
  private MenuItem armyTwoMenuItemRanged;
  @FXML
  private MenuItem armyTwoMenuItemCavalry;
  @FXML
  private TableView armyOneUnitTableView;
  @FXML
  private TableView armyTwoUnitTableView;
  @FXML
  private Button restartSimulationButton;
  @FXML
  private TextField winnerTextField;
  @FXML
  private ListView armyOneListView;
  @FXML
  private ListView armyTwoListView;

  @FXML
  protected void startSimulation(ActionEvent actionEvent) {
    if (armyOne != null && armyTwo != null && armyOne.hasUnits() && armyTwo.hasUnits()) {

      Battle battle = new Battle(armyOne, armyTwo);

      Army winner = battle.simulate();
      updateArmyOneArmyInfo();
      updateArmyTwoArmyInfo();

      winnerTextField.setText(winner.getName());
      restartSimulationButton.setDisable(false);
    } else {
      winnerTextField.setText("Both armies need units.");
    }
  }

  @FXML
  protected void restartSimulation(ActionEvent actionEvent) {
    restartSimulationButton.setDisable(true);
    winnerTextField.setText("winner");

    armyOne.getAllUnits().clear();
    armyTwo.getAllUnits().clear();

    armyOne = placeholderArmyOne;
    armyTwo = placeholderArmyTwo;

    updateArmyOneArmyInfo();
    updateArmyTwoArmyInfo();
  }

  @FXML
  protected void loadArmyOne(ActionEvent actionEvent) {
    loadArmyButton("armyOne", armyOneMenuButton, fileOneTextField);
  }

  @FXML
  protected void loadArmyTwo(ActionEvent actionEvent) {
    loadArmyButton("armyTwo", armyTwoMenuButton, fileTwoTextField);
  }

  @FXML
  protected void loadArmyOneUnitInfo(ActionEvent actionEvent) {
    setTableColumn(armyOneUnitTableView, armyOneTableColumnName, armyOneTableColumnHealth,
        armyOneTableColumnAttack, armyOneTableColumnDefence);

    String id = actionEvent.toString();

    if (id.contains("armyOneMenuItemCommander") && !(armyOne.getCommanderUnits().isEmpty())) {
      setItemsForTableView(armyOneUnitTableView, armyOne, armyOneMenuItemCommander);
    }
    if (id.contains("armyOneMenuItemInfantry") && !(armyOne.getInfantryUnits().isEmpty())) {
      setItemsForTableView(armyOneUnitTableView, armyOne, armyOneMenuItemInfantry);
    }
    if (id.contains("armyOneMenuItemRanged") && !(armyOne.getRangedUnits().isEmpty())) {
      setItemsForTableView(armyOneUnitTableView, armyOne, armyOneMenuItemRanged);
    }
    if (id.contains("armyOneMenuItemCavalry") && !(armyOne.getCavalryUnits().isEmpty())) {
      setItemsForTableView(armyOneUnitTableView, armyOne, armyOneMenuItemCavalry);
    }
  }

  @FXML
  protected void loadArmyTwoUnitInfo(ActionEvent actionEvent) {
    setTableColumn(armyTwoUnitTableView, armyTwoTableColumnName, armyTwoTableColumnHealth,
        armyTwoTableColumnAttack, armyTwoTableColumnDefence);

    String id = actionEvent.toString();

    if (id.contains("armyTwoMenuItemCommander") && !(armyTwo.getCommanderUnits().isEmpty())) {
      setItemsForTableView(armyTwoUnitTableView, armyTwo, armyTwoMenuItemCommander);
    }
    if (id.contains("armyTwoMenuItemInfantry") && !(armyTwo.getInfantryUnits().isEmpty())) {
      setItemsForTableView(armyTwoUnitTableView, armyTwo, armyTwoMenuItemInfantry);
    }
    if (id.contains("armyTwoMenuItemRanged") && !(armyTwo.getRangedUnits().isEmpty())) {
      setItemsForTableView(armyTwoUnitTableView, armyTwo, armyTwoMenuItemRanged);
    }
    if (id.contains("armyTwoMenuItemCavalry") && !(armyTwo.getCavalryUnits().isEmpty())) {
      setItemsForTableView(armyTwoUnitTableView, armyTwo, armyTwoMenuItemCavalry);
    }
  }

  @FXML
  protected void exitButton(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Exit confirmation");
    alert.setHeaderText("Exit wargames?");
    alert.setContentText("Are you sure you want to exit?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      Platform.exit();
    }
  }

  /**
   * Loads an army of units from a text file.
   * @param currentArmy Army where units will be added.
   * @param menuButton Menu button to enable after army is loaded.
   * @param loadText Text to print which file has been loaded.
   */
  private void loadArmyButton(String currentArmy, MenuButton menuButton, Text loadText) {
    FileChooser fileChooser = new FileChooser();
    Stage fileStage = new Stage();

    File selectedFile = fileChooser.showOpenDialog(fileStage);
    loadText.setText("Loaded: " + selectedFile.getName());

    if (selectedFile == null) {
      // TODO: Alert popup
      System.out.println("No file chosen.");
    } else {
      if (currentArmy.equals("armyOne")) {
        armyOne = Army.uploadArmyFromFile(selectedFile.toString());
        placeholderArmyOne = Army.uploadArmyFromFile(selectedFile.toString());
        updateArmyOneArmyInfo();
      } else {
        armyTwo = Army.uploadArmyFromFile(selectedFile.toString());
        placeholderArmyTwo = Army.uploadArmyFromFile(selectedFile.toString());
        updateArmyTwoArmyInfo();
      }
      menuButton.setDisable(false);
    }
  }

  /**
   * Puts info from units into a table.
   *
   * @param armyTwoUnitTableView table to put info in.
   * @param tableColumnName      name of unit.
   * @param tableColumnHealth    health of unit.
   * @param tableColumnAttack    attack of unit.
   * @param tableColumnDefence   defence of unit.
   */
  private void setTableColumn(TableView armyTwoUnitTableView, TableColumn tableColumnName,
                              TableColumn tableColumnHealth, TableColumn tableColumnAttack,
                              TableColumn tableColumnDefence) {
    armyTwoUnitTableView.setDisable(false);
    armyTwoUnitTableView.getItems().clear();

    tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tableColumnHealth.setCellValueFactory(new PropertyValueFactory<>("health"));
    tableColumnAttack.setCellValueFactory(new PropertyValueFactory<>("attack"));
    tableColumnDefence.setCellValueFactory(new PropertyValueFactory<>("armor"));
  }

  /**
   * General method to fill TableView with info about a specific unit
   * from an army.
   *
   * @param unitTableView Tableview to put info in.
   * @param army          Army to take info from.
   * @param menuItem      Menuitem to get ID from.
   */
  private void setItemsForTableView(TableView unitTableView, Army army, MenuItem menuItem) {

    if (menuItem.getText().equals("CommanderUnit")) {
      unitTableView.setItems(this.getObservableList(army, army.getCommanderUnits().get(0)));
    }
    if (menuItem.getText().equals("InfantryUnit")) {
      unitTableView.setItems(this.getObservableList(army, army.getInfantryUnits().get(0)));
    }
    if (menuItem.getText().equals("RangedUnit")) {
      unitTableView.setItems(this.getObservableList(army, army.getRangedUnits().get(0)));
    }
    if (menuItem.getText().equals("CavalryUnit")) {
      unitTableView.setItems(this.getObservableList(army, army.getCavalryUnits().get(0)));
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

    return units;
  }

  /**
   * Updates LisView with current info about armyOne.
   */
  private void updateArmyOneArmyInfo() {
    armyOneListView.getItems().clear();

    // Displays amount of units in List View.
    armyOneListView.getItems().add("Units: " + armyOne.getAmountOfUnits());
    armyOneListView.getItems().add("Commander units: " + armyOne.getCommanderUnits().size());
    armyOneListView.getItems().add("Infantry units: " + armyOne.getInfantryUnits().size());
    armyOneListView.getItems().add("Ranged units: " + armyOne.getRangedUnits().size());
    armyOneListView.getItems().add("Cavalry units: " + armyOne.getCavalryUnits().size());
  }

  /**
   * Updates LisView with current info about amryTwo.
   */
  private void updateArmyTwoArmyInfo() {
    armyTwoListView.getItems().clear();

    // Displays amount of units in List View.
    armyTwoListView.getItems().add("Units: " + armyTwo.getAmountOfUnits());
    armyTwoListView.getItems().add("Commander units: " + armyTwo.getCommanderUnits().size());
    armyTwoListView.getItems().add("Infantry units: " + armyTwo.getInfantryUnits().size());
    armyTwoListView.getItems().add("Ranged units: " + armyTwo.getRangedUnits().size());
    armyTwoListView.getItems().add("Cavalry units: " + armyTwo.getCavalryUnits().size());
  }
}
