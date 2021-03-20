
public class Tekstbehandler{
  private String tekst= "";
  private String tekst2= "";
  private String[] ord;
  private String[] ord2;       hei, dette  

  public Tekstbehandler(String input){
    this.tekst = input;
    this.tekst2 = input;
    tekst2 = tekst2.replaceAll("\\p{Punct}", "");
    tekst2 = tekst2.replaceAll("/,/g", "");
    tekst2 = tekst2.replaceAll("!", "");
    tekst2 = tekst2.replaceAll("\\\\?", "");
    tekst2 = tekst2.replaceAll(":", "");
    ord = tekst2.split("[ ,-]");
    ord2 = tekst.split("[:?.!]");
  }

  public int getAntallOrd(){
    int lengde = ord.length;
    return lengde;
  }

  public int getAntallOrdPeriode(){
    int lengde = ord2.length;
    return lengde;
  }

  public int getGjOrdLengde(){
    int sumOrd=0;
    for(int i=0; i<ord.length; i++){
      sumOrd += ord[i].length();
    }
    int gjennomsnitt = sumOrd/getAntallOrd();
    return gjennomsnitt;
  }

  public int getGjOrdLengdePeriode(){
    int gjennomsnitt =getAntallOrd()/getAntallOrdPeriode();
    return gjennomsnitt;
  }

  public String ordFjerning(){
    String deltekst = tekst.replaceAll("finnes", "finns");

    return deltekst +"\n";
  }

  public String getTekst(){
    return tekst+"\n";
  }

  public String getUpperCaseTekst(){
    String uppercase = tekst.toUpperCase();
    return uppercase;
  }

public String toString(){
  return "\nAntall ord i teksten: " + getAntallOrd() + "\nGjennomsnittlig ordlengde er: " + getGjOrdLengde() + "\nGjennomsnittlig ord per periode: " +getGjOrdLengdePeriode()+ "\n"+
  "\nTeksten bytter ut et ord: " + ordFjerning() + "\nOriginal tekst er: " + getTekst()+ "\nTeksten i store bokstaver: " + getUpperCaseTekst() + "\n";
}
}
