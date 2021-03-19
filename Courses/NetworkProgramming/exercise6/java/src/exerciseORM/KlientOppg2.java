package exerciseORM;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class KlientOppg2 {

    public static void main(String[]args){
        //Oppgave 2
        EntityManagerFactory emf = null;
        KontoDAO fasade = null;

        try{
            emf = Persistence.createEntityManagerFactory(("konto"));
            fasade = new KontoDAO(emf);

            Konto navnesen = new Konto("Navn navnesen", "123123", 700.00);
            Konto geir = new Konto("Geir geiresen", "532411", 650.00);


            fasade.lagNyKonto(navnesen);
            fasade.lagNyKonto(geir);


            List<Konto> liste= fasade.getAlleKontoerOverBelop(200);

            //printer ut alle kontoer funnet med saldo over 200
            for(Konto konto : liste){
                System.out.println(konto);
            }

            //Tar tak i en av disse kontoene og endrer navn
            System.out.println("\nEndrer på:");
            System.out.println(liste.get(0).toString() + "\n");

            liste.get(0).setNavn("Tøys tøyser\n");
            fasade.endreKonto(liste.get(0));

            System.out.println("Endret til:");
            System.out.println(liste.get(0).toString() + "\n");


        } catch (Exception e){
            System.out.println(e);

        } finally {
            System.out.println("\nclosing down");
            emf.close();


        }
    }
}
