class HvitRute extends Rute {
  public HvitRute(int rad, int kolonne, Labyrint labyrint) {
    super(rad, kolonne, labyrint);
  }

  @Override
  public char tilTegn() {
    return '.';
  }
}
