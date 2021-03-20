
import java.util.Random;
class Spiller {
  private int kastterningen;
  private int sumpoeng;
  private int erferdig;

    /* Konstruktør */
    public Spiller (int kastterningen, int sumpoeng, int erferdig){
      this.kastterningen = kastterningen;
      this.sumpoeng = sumpoeng;
      this.erferdig = erferdig;
    }

    /* Tilgangsmetoder */

    public int getkastTerningen(){
      java.util.Random terning = new java.util.Random();
      int terningkast = terning.nextInt(6)+1;
      kastterningen = terningkast;
      sumpoeng += kastterningen;
      return kastterningen;
    }


    public int getSumPoeng(){
      return sumpoeng;
    }


}
