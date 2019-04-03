import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;

public class Filvelger implements EventHandler<ActionEvent> {
  TextField filfelt;
  File valgtFil;

  public Filvelger(TextField filfelt) {
    this.filfelt = filfelt;
  }

  @Override
  public void handle(ActionEvent event) {
    FileChooser filvelger = new FileChooser();
    filvelger.setTitle("Velg labyrintfil");
    valgtFil = filvelger.showOpenDialog(null);

    if (valgtFil != null) {
      filfelt.setText(valgtFil.getPath());
    }
  }

  public File hentFil() {
    return valgtFil;
  }
}
