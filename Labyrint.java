import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Labyrint {
  private static Rute[][] labyrint;
  private int kolonner;
  private int rader;
  static boolean minimalUtskrift = false;

  //Oppretter en private konstruktor.
  private Labyrint(int rader, int kolonner) {
    this.rader = rader;
    this.kolonner = kolonner;
    labyrint = new Rute[rader][kolonner];
  }

  //Leser inn fra fil File.
  public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
    Scanner innfil = new Scanner(fil);

    int rad = innfil.nextInt(); //rad settes til neste int.
    int kolonne = innfil.nextInt(); //kolonne settes til neste int.

    String linje = innfil.nextLine();

    Labyrint labyrinten = new Labyrint(rad, kolonne); //Oppretter labyrint.

    //Setter ruter inn i det 2 dimensjonale arrayet for aa lage labyrinten.
    for (int i = 0; i < rad; i++) {
      linje = innfil.nextLine();
      for (int j = 0; j < kolonne; j++) {
        if (linje.charAt(j) == '#') {
          SortRute sort = new SortRute(i, j, labyrinten);
          labyrint[i][j] = sort;
        }
        else if (linje.charAt(j) == '.') {
          HvitRute hvit = new HvitRute(i, j, labyrinten);
          labyrint[i][j] = hvit;
        }
      }
    }

    //Gir hver enkelt rute en nabo.
    for (int i = 0; i < rad; i++) {
      for (int j = 0; j < kolonne; j++) {
        if ((i - 1) >= 0) {
          labyrint[i][j].settNord(labyrint[i - 1][j]);
        }
        else {
          labyrint[i][j].settNord(null);
        }

        if ((i + 1) < rad) {
          labyrint[i][j].settSyd(labyrint[i + 1][j]);
        }
        else {
          labyrint[i][j].settSyd(null);
        }

        if ((j + 1) < kolonne) {
          labyrint[i][j].settOst(labyrint[i][j + 1]);
        }
        else {
          labyrint[i][j].settOst(null);
        }

        if ((j - 1) >= 0) {
          labyrint[i][j].settVest(labyrint[i][j - 1]);
        }
        else {
          labyrint[i][j].settVest(null);
        }
      }
    }

    //Oppretter aapninger og gir disse aapningene naboer.
    for (int i = 0; i < rad; i++) {
      for (int j = 0; j < kolonne; j++) {
        if (labyrint[i][j].erAapning()) {
          Aapning aapning = new Aapning(i, j, labyrinten);
          labyrint[i][j] = aapning;

          if ((i - 1) >= 0) {
            labyrint[i][j].settNord(labyrint[i - 1][j]);
          }
          else {
            labyrint[i][j].settNord(null);
          }

          if ((i + 1) < rad) {
            labyrint[i][j].settSyd(labyrint[i +1][j]);
          }
          else {
            labyrint[i][j].settSyd(null);
          }

          if ((j + 1) < kolonne) {
            labyrint[i][j].settOst(labyrint[i][j + 1]);
          }
          else {
            labyrint[i][j].settOst(null);
          }

          if ((j - 1) >= 0) {
            labyrint[i][j].settVest(labyrint[i][j - 1]);
          }
          else {
            labyrint[i][j].settVest(null);
          }
        }
      }
    }
    return labyrinten;
  }

  //Printer ut labyrinten.
  @Override
  public String toString() {
    String utskrift = "";

    for (int i = 0; i < rader; i++) {
      if (utskrift != "") {
        utskrift += System.lineSeparator();
      }
      for (int j = 0; j < kolonner; j++) {
        utskrift += labyrint[i][j].tilTegn();
      }
    }
    return utskrift;
  }

  //Finner utveier fra (kol, rad) og returnerer disse.
  public Koe<String> finnUtveiFra(int kol, int rad) {
    kol = kol - 1;
    rad = rad - 1;

    return labyrint[rad][kol].finnUtvei();
  }

  //Setter minimalUtskrift til true.
  public void settMinimalUtskrift() {
    minimalUtskrift = true;
  }

  //Returnerer minimalUtskrift.
  public boolean hentMinimalUtskrift() {
    return minimalUtskrift;
  }

  public int hentRader() {
    return rader;
  }

  public int hentKolonner() {
    return kolonner;
  }

  public Rute hentRute(int rad, int kolonne) {
    return labyrint[rad][kolonne];
  }
}
