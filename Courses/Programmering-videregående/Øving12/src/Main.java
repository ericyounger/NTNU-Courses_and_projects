import dyrehage.SkandinaviskeRovdyr;
import dyrehage.*;

public class Main {

    public static void main(String[] args) {

      //Oppretter individer gjennom Rovdyrfabrikk
      Rovdyrfabrikk a = new Rovdyrfabrikk();
      SkandinaviskeRovdyr trine = a.nyBinne(20180112, "Bur 1", "Trine", 19900112, 4);
      SkandinaviskeRovdyr sebastian = a.nyHannbjørn(20190304, "Bur 2", "Sebastian", 19852006);
      SkandinaviskeRovdyr torleif = a.nyUlvehann(20170612, "Bur 5", "Torleif", 19950706);
      SkandinaviskeRovdyr cecilie = a.nyUlvetispe(20030609, "Bur 6", "Cecilie",20000103,3);

      //Oppretter dyregrupper fra klassen dyregrupper


      //Oppretter stim og flokk
      Fiskestim gullfisk = new Fiskestim("Gullfisk", "Gullfiskium latoilet", "Toiletus",20130312,"Akvarium 1","Gullfisk",200,6,true);
      Fiskestim piraya = new Fiskestim("Piraya", "Piranha Piraya", "Piratus", 20140610,"Akvarium 2", "Piraya", 70,12,false);

      Fugleflokk due = new Fugleflokk("Due", "Pigeonous Selektus", "Pigeoni", 20150211,"Innhengning 4", "Duer", 40,4,false);
      Fugleflokk pelikan  = new Fugleflokk("Pelikan","Pelikanus svommus","Peli",20140201,"Innhengning 2", "Pelikaner",35,14,true);

      Hannindivid kenguru = new Hannindivid("Kenguru", "Kenguri pilates", "Kenguri", 20090402,"Hage 1","Kenguru",20030402,true,false);
      Hunnindivid geit = new Hunnindivid("Geit","Geitus tullerus","Geitus",20070402,"Hage 1","Geit",20010407,false,false,4);

      System.out.println("Adresseendring individ test");
      System.out.println(geit.getAdresse());
      geit.setAdresse("Hage2");
      System.out.println(geit.getAdresse() + "\n");

      System.out.println("Adresseendring gruppe test");
      System.out.println(gullfisk.getAdresse());
      gullfisk.setAdresse("Akvarium 3");
      System.out.println(gullfisk.getAdresse()+ "\n");


      System.out.println("get fDato test");
      System.out.println(geit.getFDato()+ "\n");

      System.out.println("Rovdyr:");
      System.out.println(trine);
      System.out.println(sebastian);
      System.out.println(torleif);
      System.out.println(cecilie);

      System.out.println("\nFiskestimer");
      System.out.println(gullfisk);
      System.out.println(piraya);

      System.out.println("\nFugleflokk:");
      System.out.println(due);
      System.out.println(pelikan);

      System.out.println("\nIndivid:");
      System.out.println(kenguru);
      System.out.println(geit);

      System.out.println("\nTest kull status:");
      System.out.println(trine.getNavn()+ " " + trine.getAntKull());
      System.out.println(sebastian.getNavn() + " " +sebastian.getAntKull());
      System.out.println(torleif.getNavn() + " " +torleif.getAntKull());
      System.out.println(cecilie.getNavn() + " " + cecilie.getAntKull());


      System.out.println("\nLegger til kull");
      trine.leggTilKull(5);
      sebastian.leggTilKull(5);
      torleif.leggTilKull(5);
      cecilie.leggTilKull(5);

      System.out.println("\nTest kull status etter kull er lagt til:");
      System.out.println(trine.getNavn()+ " " + trine.getAntKull());
      System.out.println(sebastian.getNavn() + " " +sebastian.getAntKull());
      System.out.println(torleif.getNavn() + " " +torleif.getAntKull());
      System.out.println(cecilie.getNavn() + " " + cecilie.getAntKull());


      System.out.println("\nØk kull med 1 status etter forrige status:");
      trine.leggTilNyttKull();
      sebastian.leggTilNyttKull();
      torleif.leggTilNyttKull();
      cecilie.leggTilNyttKull();

      System.out.println("\nTest kull status etter kull er inkrementert med 1:");
      System.out.println(trine.getNavn()+ " " + trine.getAntKull());
      System.out.println(sebastian.getNavn() + " " +sebastian.getAntKull());
      System.out.println(torleif.getNavn() + " " +torleif.getAntKull());
      System.out.println(cecilie.getNavn() + " " + cecilie.getAntKull());




    }
}
