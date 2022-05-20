package no.ntnu.idatx2001.wargames.ui.dialog;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import no.ntnu.idatx2001.wargames.model.Army;
import no.ntnu.idatx2001.wargames.model.UnitFactory;
import no.ntnu.idatx2001.wargames.model.units.*;

import java.io.IOException;
import java.util.List;

public class AddUnitDialog extends Dialog<List<Unit>> {

  private String unitType = "";
  private String unitName = "";
  private int unitAmount = 0;
  private int unitHealth = 0;

  @FXML
  private TextField unitNameTextField;
  @FXML
  private TextField unitAmountTextField;
  @FXML
  private TextField unitHealthTextField;
  @FXML
  private ComboBox<String> unitTypeCB;

  /**
   * Creates an instance of AddUnit Dialog.
   */
  public AddUnitDialog() {
    super();
    loadWindow();
    defineReturnData();
  }

  /**
   * Loads the window from an .fxml file.
   * Applies .css file, content and OK and CANCEL buttons.
   */
  private void loadWindow() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("addUnitDialogPane.fxml"));
      loader.setController(this);

      DialogPane dialogPane = loader.load();
      getDialogPane().getStylesheets().add(
          getClass().getResource("dialogStyle.css").toExternalForm()
      );

      getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

      setTitle("Add Units");

      setMenuItems();

      // Makes sure you can only input integers into the text field
      setIntegerOnly(unitAmountTextField);
      setIntegerOnly(unitHealthTextField);

      getDialogPane().setContent(dialogPane);
    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }

  /**
   * Only allows integers to be input into text-input.
   *
   * @param textField TextField to put integer-checker on
   */
  private void setIntegerOnly(TextField textField) {
    textField.textProperty().addListener((
        observable, oldValue, newValue) -> {
      try {
        if (newValue.length() > 0) {
          Integer.parseInt(newValue);
        }
      } catch (NumberFormatException e) {
        // The value is not updated
        textField.setText(oldValue);
      }
    });
  }

  /**
   * Fills the ComboBox with Unit-types.
   */
  private void setMenuItems() {
    this.unitTypeCB.setItems(FXCollections.observableArrayList(
        "Commander Unit",
        "Infantry Unit",
        "Ranged Unit",
        "Cavalry Unit",
        "Artillery Unit"
    ));
  }

  private void addUnitErrorAlert() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("ERROR");
    alert.setHeaderText("There was an error when adding an unit!");
    alert.setContentText("Make sure you fill out all input fields when adding units!");
    alert.showAndWait();
  }

  /**
   * Defines the list of units to be returned.
   */
  private void defineReturnData() {
    setResultConverter(
        (ButtonType button) -> {
          List<Unit> result = null;
          if (button == ButtonType.OK) {
            if (unitTypeCB.getValue() != null
                && !unitNameTextField.getText().equals("")
                && !unitAmountTextField.getText().equals("")
                && !unitHealthTextField.getText().equals("")) {
              this.unitType = unitTypeCB.getValue().replace(" ", "");
              this.unitName = unitNameTextField.getText();
              this.unitAmount = Integer.parseInt(unitAmountTextField.getText());
              this.unitHealth = Integer.parseInt(unitHealthTextField.getText());

              result = new UnitFactory().createUnitBattalion(
                  unitType, unitAmount, unitName, unitHealth
              );
            } else {
              addUnitErrorAlert();
            }
          }
          return result;
        }
    );
  }
}
