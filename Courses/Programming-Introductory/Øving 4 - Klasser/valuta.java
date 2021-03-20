

class valuta {
  /* Initierer objektvariabler */
  private final String type;
  private final double multiplikator;


  /* Konstruktør */
    public valuta (String type, double multiplikator) {
    this.type = type;
    this.multiplikator = multiplikator;
  }

  public String getType(){
    return type;
  }

  public double getNoktilvaluta() {
    double belopkonvertert = valutaprogram.belop * multiplikator;
    return belopkonvertert;
  }

  public double getNokfravaluta() {
    double belopkonvertert = valutaprogram.belop / multiplikator;
    return belopkonvertert;
  }
  public double getMultiplikator(){
    return multiplikator;
  }



}
