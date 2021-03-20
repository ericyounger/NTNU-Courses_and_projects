import static javax.swing.JOptionPane.*;

public class ArbTaker{
  private Person personalia;
  private final int arbTakerNr;
  private final int ansettelsesAar;
  private double manedslonn;
  private int skatteProsent;
  private static final String[] MULIGHETER ={"Dette", "dette", "elller", "dette"};
  private String[] options = {"Ansatt gitt år", "Endre skatteprosent", "Endre månedslønn", "Se skattetrekk", "Personal info"};

  public ArbTaker(Person personalia, int arbTakerNr, int ansettelsesAar, double manedslonn, int skatteProsent){
    this.personalia = personalia;
    this.arbTakerNr = arbTakerNr;
    this.ansettelsesAar = ansettelsesAar;
    this.manedslonn = manedslonn;
    this.skatteProsent = skatteProsent;
  }


  public int getArbTakerNr(){
    
    return arbTakerNr;
  }

  public int getAnsettelsesAar(){
    return ansettelsesAar;
  }

  public int getAarAnsatt(){
    java.util.GregorianCalendar kalender = new java.util.GregorianCalendar();
    int aar = kalender.get(java.util.Calendar.YEAR);
    int lengdeAnsettelse = aar-getAnsettelsesAar();
    return lengdeAnsettelse;
  }

  public double getManedslonn(){
    return manedslonn;
  }

  public int getSkatteProsent(){
    return skatteProsent;
  }

  public void setManedslonn(double lonn){
    this.manedslonn = lonn;
  }

  public int getAlder(){
    java.util.GregorianCalendar kalender = new java.util.GregorianCalendar();
    int aar = kalender.get(java.util.Calendar.YEAR);
    int alder = aar-personalia.getFodselsaar();
    return alder;
  }

  public void setSkatteProsent(int skatteprosent){
    this.skatteProsent = skatteprosent;
  }

  public double getBruttoLonn(){
    double bruttolonn =getManedslonn()*100/(100-getSkatteProsent());
  return bruttolonn;
  }

  public double getArligSkattetrekk(){
    double sum = (getBruttoLonn()*getSkatteProsent()/100)*10 +getBruttoLonn()*(getSkatteProsent()/2)/100;
    return sum;
  }

  public double getMndSkattetrekk(){
    double vanligMnd = getBruttoLonn()*getSkatteProsent()/100;
    return vanligMnd;
  }

  public double getMndSkattetrekkDes(){
    double desMnd = getBruttoLonn()*getSkatteProsent()/2/100;
    return desMnd;
  }

  public void getBGS(){

    int valg = showOptionDialog(null, personalia.getFornavn()+ " " +personalia.getEtternavn(), "Velg en av disse", 0, PLAIN_MESSAGE, null, options, options[4]);




    switch(valg){
      case 0:
        String messageLest = showInputDialog(null, "Sjekk om  " +personalia.getFornavn() +" har vært ansatt gitt år. \nTast inn hele år:");
        int message = Integer.parseInt(messageLest);
        getAnsattInput(message);
        break;
      case 1:
        showMessageDialog(null, "Skatteprosenten din er: " +getSkatteProsent() );
        String nySkatteProsentLest = showInputDialog(null, "Skriv inn den nye skatteprosenten: ");
        int nySkatteProsent = Integer.parseInt(nySkatteProsentLest);
        setSkatteProsent(nySkatteProsent);
        showMessageDialog(null,"Din nye skatteprosent er nå: " + getSkatteProsent());
        break;
      case 2:
        String nyMndLonnLest = showInputDialog(null, "Tast inn ny månedslønn: ");
        double nyMndLonn = Double.parseDouble(nyMndLonnLest);
        setManedslonn(nyMndLonn);
        double manedslonn2 = getManedslonn();
        showMessageDialog(null,"Ny månedslønn er nå: " + manedslonn2);
        break;
      case 3:
      showMessageDialog(null, "Årlig skattetrekk: " +getArligSkattetrekk()+"\n" + "\nVanlig måned skattetrekk: " +getMndSkattetrekk() + "\nJuni Skattetrekk: 0" + "\nDesember skattetrekk: " + getMndSkattetrekkDes());
        break;
      case 4:
        object();
        break;
      default:

      break;

    }
  }

  public void getAnsattInput(int input){
    if(getAarAnsatt()<input){
      showMessageDialog(null, "Nei, han/hun har vært ansatt i " +getAarAnsatt() +" år");
    } else if(getAarAnsatt()==input){
        showMessageDialog(null, "Ja, han/hun har vært ansatt " +getAarAnsatt() + " år");
    } else if(getAarAnsatt()>input){
      showMessageDialog(null, "Han/hun har vært ansatt over " +input +" år, og har vært ansatt i " +getAarAnsatt() + " år");
    }

  }

  public void object(){
    showMessageDialog(null, personalia.getEtternavn() + ", " +personalia.getFornavn() + "\nAlder: " +getAlder() +"\nArbeidstaker nr: " + getArbTakerNr()+ "\nBrutto Månedslønn: " +getBruttoLonn() + "\nNetto Månedslønn: " +getManedslonn()+ "\nSkatteprosent: "
    +getSkatteProsent() + "\nÅr ansatt i bedriften: "+getAarAnsatt());
  }

  public String toString(){
    return personalia.getEtternavn() + ", " +personalia.getFornavn() + "\nAlder: " +getAlder() +"\nArbeidstaker nr: " + getArbTakerNr()+ "\nBrutto Månedslønn: " +getBruttoLonn() + "\nNetto Månedslønn: " +getManedslonn()+ "\nSkatteprosent: "
    +getSkatteProsent() + "\nÅr ansatt i bedriften: "+getAarAnsatt();
  }

}
