module no.ntnu.idatx2001.wargames {
  requires javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;

  opens no.ntnu.idatx2001.wargames.model;
  opens no.ntnu.idatx2001.wargames.model.units to javafx.base;

  opens no.ntnu.idatx2001.wargames.ui to javafx.graphics, javafx.fxml;
  opens no.ntnu.idatx2001.wargames.ui.dialog to javafx.graphics, javafx.fxml;

  exports no.ntnu.idatx2001.wargames.ui.dialog to javafx.fxml;
  exports no.ntnu.idatx2001.wargames.ui to javafx.fxml;
  exports no.ntnu.idatx2001.wargames.model;
  exports no.ntnu.idatx2001.wargames.model.units to javafx.base;
}
