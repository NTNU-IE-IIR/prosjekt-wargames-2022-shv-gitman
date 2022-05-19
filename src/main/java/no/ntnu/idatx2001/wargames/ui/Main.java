package no.ntnu.idatx2001.wargames.ui;

import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Responsible for starting the program.
 */
public class Main extends Application {

  /**
   * Launches the program
   * ** T O D O **
   *
   * @param args args.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Parent root = null;
    try {
      root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/gui.fxml")));

      primaryStage.setTitle("Wargames");
      primaryStage.setScene(new Scene(root, 800, 600));
      primaryStage.show();

    } catch (IOException e) {
      System.out.println("ERROR: " + e.getMessage());
    }
  }
}
