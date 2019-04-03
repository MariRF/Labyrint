abstract class Rute {
  protected int rad; //y
  protected int kolonne; //x
  protected Labyrint labyrint; //Referanse til labyrinten den tilhorer.
  protected Rute nord; //Nabo nord.
  protected Rute syd; //Nabo syd.
  protected Rute ost; //Nabo ost.
  protected Rute vest; //Nabo vest.
  private static Koe<String> utveier;

  public Rute(int rad, int kolonne, Labyrint labyrint) {
    this.kolonne = kolonne;
    this.rad = rad;
    this.labyrint = labyrint;
  }

  //Metode for aa gaa til alle naboruter
  public void gaa(Rute forrige, String kordinater) {
    String vei = "";
    
    if (kordinater.equals("")) {
      vei = hentKordinater();
    }
    else {
      vei = kordinater + " --> " + hentKordinater();
    }

    //Hvis ikke minimalUtskrift == true skriv ut vei.
    if (!labyrint.hentMinimalUtskrift()) {
      System.out.println(vei);
    }

    //Hvis erAapning == true legg til kordinater paa veien.
    if (erAapning()) {

      utveier.settInn(vei);
    }

    //Hvis ruten er hvit og den forrige ikke er lik en retning, gaa denne retningen.
    else if (this instanceof HvitRute) {
      if (forrige != nord) {
        nord.gaa(this, vei);
      }
      if (forrige != syd) {
        syd.gaa(this, vei);
      }
      if (forrige != ost) {
        ost.gaa(this, vei);
      }
      if (forrige != vest) {
        vest.gaa(this, vei);
      }
    }
  }

  //Kall paa gaa og returner alle utveier.
  public Koe<String> finnUtvei() {
    utveier = new Koe<String>();
    gaa(null, "");
    return utveier;
  }

  //Metode for aa sjekke om ruten er en aapning.
  public boolean erAapning() {
    return this instanceof HvitRute && (this.nord == null || this.syd == null || this.ost == null || this.vest == null);
  }

  //Setter nabo nord.
  public void settNord(Rute nord) {
    this.nord = nord;
  }

  //Setter nabo syd.
  public void settSyd(Rute syd) {
    this.syd = syd;
  }

  //Setter nabo ost.
  public void settOst(Rute ost) {
    this.ost = ost;
  }

  //Setter nabo vest.
  public void settVest(Rute vest) {
    this.vest = vest;
  }

  //Hent rad til ruten.
  public int hentRad() {
    return rad + 1;
  }

  //Hent kolonne til ruten.
  public int hentKolonne() {
    return kolonne + 1;
  }

  //Returnerer kordinatene til ruten.
  public String hentKordinater() {
    return "(" + hentKolonne() + ", " + hentRad() + ")";
  }

  abstract public char tilTegn();
}
