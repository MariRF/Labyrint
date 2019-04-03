class SortRute extends Rute {
  public SortRute(int rad, int kolonne, Labyrint labyrint) {
    super(rad, kolonne, labyrint);
  }

  @Override
  public char tilTegn() {
    return '#';
  }
}
