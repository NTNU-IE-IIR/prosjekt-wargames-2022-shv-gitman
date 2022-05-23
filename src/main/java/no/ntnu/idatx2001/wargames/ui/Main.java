package no.ntnu.idatx2001.wargames.ui;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Responsible for starting the program.
 */
public class Main extends Application {

  private Stage primaryStage;

  /**
   * Launches the application.
   *
   * @param args args.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setOnCloseRequest(exitApplication);

    Parent root;
    try {
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/gui.fxml")));

      primaryStage.setTitle("Wargames");
      primaryStage.setScene(new Scene(root, 800, 650));
      primaryStage.show();

    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }


  /**
   * Presents the user with a confirmation alert when the user press the exit button.
   * If the user confirms exit, all temp files are deleted.
   */
  private final EventHandler<WindowEvent> exitApplication = event -> {
    Alert closeWindowAlert = new Alert(
        Alert.AlertType.CONFIRMATION, "Are you sure you want to exit Wargames?"
    );
    closeWindowAlert.setHeaderText("Confirm exit");
    closeWindowAlert.initModality(Modality.APPLICATION_MODAL);
    closeWindowAlert.initOwner(primaryStage);

    Optional<ButtonType> closeResponse = closeWindowAlert.showAndWait();


    if (!ButtonType.OK.equals(closeResponse.get())) {
      event.consume();
    } else {
      // Deletes temp army files when application is closed.
      MainPageController.deleteFolderContent(new File("army-templates/temp/"));
    }
  };
}
