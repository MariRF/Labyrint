import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.FileNotFoundException;
import java.io.File;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.input.MouseEvent;


public class Oblig7 extends Application {
  Stage stage;
  VBox root;
  Labyrint labyrint;
  int storrelse = 50;
  GUIRute[][] ruter;

  @Override
  public void start(Stage stage) {
    this.stage = stage;
    HBox toppBoks = lagToppBoks();
    root = new VBox(toppBoks);

    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.setTitle("Labyrint");
    stage.show();
  }

  public HBox lagToppBoks() {
    TextField filFelt = new TextField();
    Filvelger filvelger = new Filvelger(filFelt);
    Button velgFilKnapp = new Button("Velg fil...");
    velgFilKnapp.setOnAction(filvelger);
    Button lastInnKnapp = new Button("Last inn");

    lastInnKnapp.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          labyrint = Labyrint.lesFraFil(filvelger.hentFil());
        } catch (FileNotFoundException e) {
          System.out.println("FileNotFoundException");
        }
        GridPane rutenett = new GridPane();
        ruter = new GUIRute[labyrint.hentRader()][labyrint.hentKolonner()];
        for (int rad = 0; rad < labyrint.hentRader(); rad++) {
          for (int kolonne = 0; kolonne < labyrint.hentKolonner(); kolonne++) {
            GUIRute rute = new GUIRute(rad, kolonne);
            ruter[rad][kolonne] = rute;
            if (labyrint.hentRute(rad, kolonne) instanceof SortRute) {
              rute.endreFarge();
            }
            rutenett.add(ruter[rad][kolonne], kolonne, rad);
          }
        }
        root.getChildren().add(rutenett);
        stage.sizeToScene();

      }
    });

    HBox returBoks = new HBox(50, velgFilKnapp, filFelt, lastInnKnapp);
    return returBoks;
  }

  static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
    boolean[][] losning = new boolean[hoyde][bredde];
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
    java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s", ""));
    while (m.find()) {
      int x = Integer.parseInt(m.group(1)) - 1;
      int y = Integer.parseInt(m.group(2)) - 1;
      losning[y][x] = true;
    }
    return losning;
  }

  private class GUIRute extends Pane {
    private boolean erHvit;
    private int rad;
    private int kolonne;

    public GUIRute(int rad, int kolonne) {
      super();
      this.rad = rad;
      this.kolonne = kolonne;

      setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
      setMinWidth(storrelse);
      setMinHeight(storrelse);

      addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          labyrint.settMinimalUtskrift();
          Koe<String> utveier = labyrint.finnUtveiFra(kolonne + 1, rad + 1);

          if (!utveier.erTom()) {
            boolean[][] utvei = losningStringTilTabell(utveier.fjern(), labyrint.hentKolonner(), labyrint.hentRader());

            for (int rad = 0; rad < labyrint.hentRader(); rad++) {
              for (int kolonne = 0; kolonne < labyrint.hentKolonner(); kolonne++) {
                if (utvei[rad][kolonne]) {
                  ruter[rad][kolonne].settUtvei();
                }
              }
            }
          }
          else {
            System.out.println("Ingen utveier!");
          }
        }
      });
    }

    public void endreFarge() {
      erHvit = false;
      setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }

    public void settUtvei() {
      setBackground(new Background(new BackgroundFill(Color.GREY, null, null)));
    }
  }
}
