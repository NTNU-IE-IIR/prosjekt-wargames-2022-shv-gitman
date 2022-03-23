module JavaFX.Events {
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  opens org.ntnu.wargames to javafx.fxml;
  exports org.ntnu.wargames;
}
