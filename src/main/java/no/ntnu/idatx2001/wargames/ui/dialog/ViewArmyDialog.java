package no.ntnu.idatx2001.wargames.ui.dialog;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.units.*;
import no.ntnu.idatx2001.wargames.ui.MainPageController;

public class ViewArmyDialog extends Dialog<Army> {

  private final Army currentArmy;

  @FXML
  private Text textHeader;
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
  private ComboBox<String> unitTypeCB;

  /**
   * Creates an instance of the ViewArmy Dialog to get information
   * about the units in the army.
   * @param army army to get information from
   */
  public ViewArmyDialog(Army army) {
    super();
    this.currentArmy = army;
    loadWindow();
    defineReturnData();
  }

  /**
   * Fills the table column when a unit type is selected from the combo box.
   *
   * @param actionEvent event
   */
  @FXML
  protected void selectUnitType(ActionEvent actionEvent) {
    setTableColumn();
  }

  /**
   * Loads the window from an .fxml file.
   * Applies .css file, content and a CANCEL button.
   */
  private void loadWindow() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("viewArmyDialogPane.fxml"));
      loader.setController(this);

      DialogPane dialogPane = loader.load();
      getDialogPane().getStylesheets().add(
          getClass().getResource("dialogStyle.css").toExternalForm()
      );

      getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);

      setTitle("View Army: " + this.currentArmy.getName());

      setMenuItems();

      getDialogPane().setContent(dialogPane);
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

  /**
   * Fills the ComboBox with Unit-types.
   */
  private void setMenuItems() {
    unitTypeCB.setItems(FXCollections.observableArrayList(
        "Commander Unit",
        "Infantry Unit",
        "Ranged Unit",
        "Cavalry Unit",
        "Artillery Unit"
    ));
  }

  /**
   * Puts info from all units of a selected Unit-type into a table.
   */
  private void setTableColumn() {
    nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    healthTableColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
    armorTableColumn.setCellValueFactory(new PropertyValueFactory<>("attack"));
    attackTableColumn.setCellValueFactory(new PropertyValueFactory<>("armor"));

    if (unitTypeCB.getValue().equals("Commander Unit")
        && !(currentArmy.getCommanderUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(this.currentArmy, this.currentArmy.getCommanderUnits().get(0))
      );
    } else if (unitTypeCB.getValue().equals("Infantry Unit")
        && !(currentArmy.getInfantryUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(this.currentArmy, this.currentArmy.getInfantryUnits().get(0))
      );
    } else if (unitTypeCB.getValue().equals("Ranged Unit")
        && !(currentArmy.getRangedUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(this.currentArmy, this.currentArmy.getRangedUnits().get(0))
      );
    } else if (unitTypeCB.getValue().equals("Cavalry Unit")
        && !(currentArmy.getCavalryUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(this.currentArmy, this.currentArmy.getCavalryUnits().get(0))
      );
    } else if (unitTypeCB.getValue().equals("Artillery Unit")
        && !(currentArmy.getArtilleryUnits().isEmpty())) {
      unitTableView.setItems(
          getObservableList(this.currentArmy, this.currentArmy.getArtilleryUnits().get(0))
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
    return MainPageController.getUnits(army, unit);
  }

  // TODO: Useless?
  private void defineReturnData() {
    setResultConverter(
        (ButtonType button) -> null
    );
  }
}
