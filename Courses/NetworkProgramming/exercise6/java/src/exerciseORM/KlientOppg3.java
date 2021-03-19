package exerciseORM;

import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import java.util.List;

public class KlientOppg3 {
    public static void main(String[]args){
        //Oppgave 2
        EntityManagerFactory emf = null;
        KontoDAO fasade = null;

        try{
            emf = Persistence.createEntityManagerFactory(("konto"));
            fasade = new KontoDAO(emf);

            List<Konto> liste= fasade.getAlleKontoerOverBelop(200);

            liste.get(0).trekk(100);
            liste.get(1).settInn(100);

            //Thread.sleep(5000); For å få feil uten lås, legger til og fjerner  denne når jeg kjører to klienter for å time transaksjonen

            fasade.endreKonto(liste.get(0));
            fasade.endreKonto(liste.get(1));




        } catch (OptimisticLockException e){
            System.out.println(e);
            //Feilhåndtering bør skje her.
        } catch (RollbackException e){
            System.out.println(e);
            //Feilhåndtering bør skje her
        }

        catch (Exception e){
            System.out.println(e);

        } finally {
            System.out.println("\nclosing down");
            emf.close();


        }
    }
}
