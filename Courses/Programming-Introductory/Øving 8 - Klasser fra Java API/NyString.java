
public class NyString{
  private final String tekst;

  public NyString(String input){
    this.tekst = input;
  }

  public String getTekst(){
  return tekst;
  }


  public String forkortelse(){
    String ut="";
    String[] ord = tekst.split("[ .,-]");
    for(int i=0; i<ord.length; i++){
      ut += ord[i].charAt(0);
    }
    return ut;
    }

    public String tegnFjerning(){
      int pos= tekst.indexOf('e');
      String deltekst = tekst.replaceAll("e", "");
      return deltekst;
    }

}
